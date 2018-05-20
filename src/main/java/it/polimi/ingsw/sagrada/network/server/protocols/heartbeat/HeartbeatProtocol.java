package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import java.util.logging.Logger;

class HeartbeatProtocol implements Runnable, Observable<HeartbeatState, HeartbeatEvent>, NetworkUtils {

    private static final int TIME_INTERVAL = 1000;
    private static final int TIME_LIFE_FRAMES = 30;
    private static final int TIME_LOSS_COMMUNICATION_FRAME = 10;

    private DatagramSocket datagramSocket;
    private ExecutorService executor;
    private String data;
    private Observer<HeartbeatState, HeartbeatEvent> observer;

    HeartbeatProtocol(DatagramSocket datagramSocket, Observer<HeartbeatState, HeartbeatEvent> observer) {
        executor = Executors.newSingleThreadExecutor();
        this.datagramSocket = datagramSocket;
        this.observer = observer;
    }

    /**
     * @return received heartbeat
     */
    private byte[] receiveHeartbeat() throws IOException{
        byte[] payload = receiveData(datagramSocket);
        data = new String(payload);
        return payload;
    }

    /**
     * @apiNote listen for one host heartbeat and monitor it, launching events
     */
    @Override
    public void run() {
        Future asynchronousHeartbeat = executor.submit(this::receiveHeartbeat);
        boolean lossCommsAlreadyNotified = false;
        boolean isDead = false;
        int timeElapsed = 0;
        HeartbeatEvent event;
        while (!isDead) {
            while (!asynchronousHeartbeat.isDone()) {
                try {
                    Thread.sleep(TIME_INTERVAL);
                } catch (InterruptedException exc) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, exc.getMessage());
                    event = new HeartbeatEvent(data, timeElapsed, new Date().getTime());
                    notify(HeartbeatState.HOST_OFFLINE, event);
                    asynchronousHeartbeat.cancel(true);
                    Thread.currentThread().interrupt();
                }
                timeElapsed += TIME_INTERVAL;
                if (timeElapsed > TIME_LOSS_COMMUNICATION_FRAME * TIME_INTERVAL && !lossCommsAlreadyNotified)
                    lossCommsAlreadyNotified = notifyLostComm(timeElapsed);

                if (timeElapsed > TIME_LIFE_FRAMES * TIME_INTERVAL)
                   isDead = notifyDeath(timeElapsed, asynchronousHeartbeat);
            }
            if(!isDead) {
                notifyHeartbeat(lossCommsAlreadyNotified, timeElapsed);
                lossCommsAlreadyNotified = false;
                timeElapsed = 0;
                asynchronousHeartbeat = executor.submit(this::receiveHeartbeat);
            }
        }
    }

    private boolean notifyLostComm(int timeElapsed) {
        HeartbeatEvent event = new HeartbeatEvent(data, timeElapsed, new Date().getTime());
        notify(HeartbeatState.COMMUNICATION_LOST, event);
        return true;
    }

    private boolean notifyDeath(int timeElapsed, Future asynchronousHeartbeat) {
        HeartbeatEvent event = new HeartbeatEvent(data, timeElapsed, new Date().getTime());
        notify(HeartbeatState.HOST_OFFLINE, event);
        asynchronousHeartbeat.cancel(true);
        return true;
    }

    private void notifyHeartbeat(boolean lossCommsAlreadyNotified, int timeElapsed) {
        HeartbeatEvent event = new HeartbeatEvent(data, timeElapsed, new Date().getTime());
        if (lossCommsAlreadyNotified)
            notify(HeartbeatState.HOST_ONLINE, event);
        else
            notify(HeartbeatState.HEARTBEAT_RECEIVED, event);
    }

    @Override
    public void notify(HeartbeatState heartbeatState, HeartbeatEvent event) {
        observer.update(heartbeatState, event);
    }
}

