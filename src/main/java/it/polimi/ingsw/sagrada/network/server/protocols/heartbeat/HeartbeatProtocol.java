package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * The Class HeartbeatProtocol.
 */
class HeartbeatProtocol implements Runnable, Observable<HeartbeatState, HeartbeatEvent> {

    /** The Constant TIME_INTERVAL. */
    private static final int TIME_INTERVAL = 1000;
    
    /** The Constant TIME_LIFE_FRAMES. */
    private static final int TIME_LIFE_FRAMES = 5;
    
    /** The Constant TIME_LOSS_COMMUNICATION_FRAME. */
    private static final int TIME_LOSS_COMMUNICATION_FRAME = 3;
    
    /** The Constant UDP_VALID_PACKET_SIZE. */
    private static final int UDP_VALID_PACKET_SIZE  = 1024;

    /** The datagram socket. */
    private DatagramSocket datagramSocket;
    
    /** The port. */
    private int port;
    
    /** The executor. */
    private ExecutorService executor;
    
    /** The data. */
    private String data;
    
    /** The expected payload. */
    private String expectedPayload;
    
    /** The observer. */
    private Observer<HeartbeatState, HeartbeatEvent> observer;
    
    /** The runnable worker thread. */
    private Thread runnableWorkerThread;
    
    /** The is dead. */
    private boolean isDead = false;

    /**
     * Instantiates a new heartbeat protocol.
     *
     * @param port the port
     * @param observer the observer
     * @param expectedPayload the expected payload
     * @throws IOException Signals that an I/O exception has occurred.
     */
    HeartbeatProtocol(int port, Observer observer, String expectedPayload) throws IOException{
        executor = Executors.newSingleThreadExecutor();
        this.port = port;
        datagramSocket = new DatagramSocket(port);
        this.observer = observer;
        this.expectedPayload = expectedPayload;
        runnableWorkerThread = null;
    }

    /**
     * Kill.
     */
    void kill() {
        executor.shutdownNow();
        isDead = true;
        runnableWorkerThread.interrupt();
    }

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Receive heartbeat.
     *
     * @return received heartbeat
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private byte[] receiveHeartbeat() throws IOException {
        byte[] payload = receiveData(datagramSocket);
        data = new String(payload);
        return payload;
    }

    /**
     * Run.
     *
     * @apiNote listen for one host heartbeat and monitor it, launching events
     */
    @Override
    public void run() {
        Future asynchronousHeartbeat = executor.submit(this::receiveHeartbeat);
        boolean lossCommAlreadyNotified = false;
        int timeElapsed = 0;
        HeartbeatEvent event;
        runnableWorkerThread = Thread.currentThread();
        while (!isDead && !executor.isShutdown()) {
            while (!asynchronousHeartbeat.isDone()) {
                try {
                    Thread.sleep(TIME_INTERVAL);
                } catch (InterruptedException exc) {
                    if(data.equals(expectedPayload)) {
                        event = new HeartbeatEvent(data, timeElapsed, new Date().getTime());
                        notify(HeartbeatState.HOST_OFFLINE, event);
                        asynchronousHeartbeat.cancel(true);
                        Thread.currentThread().interrupt();
                        isDead = true;
                    }
                }
                timeElapsed += TIME_INTERVAL;
                if (timeElapsed > TIME_LOSS_COMMUNICATION_FRAME * TIME_INTERVAL && !lossCommAlreadyNotified)
                    lossCommAlreadyNotified = notifyLostComm(timeElapsed);

                if (timeElapsed > TIME_LIFE_FRAMES * TIME_INTERVAL)
                    isDead = notifyDeath(timeElapsed, asynchronousHeartbeat);
            }
            if(!isDead && data.equals(expectedPayload)) {
                notifyHeartbeat(lossCommAlreadyNotified, timeElapsed);
                lossCommAlreadyNotified = false;
                timeElapsed = 0;
            }
            asynchronousHeartbeat = executor.submit(this::receiveHeartbeat);
        }
        asynchronousHeartbeat.cancel(true);
    }

    /**
     * Notify lost comm.
     *
     * @param timeElapsed the time elapsed
     * @return true, if successful
     */
    private boolean notifyLostComm(int timeElapsed) {
        HeartbeatEvent event = new HeartbeatEvent(expectedPayload, timeElapsed, new Date().getTime());
        notify(HeartbeatState.COMMUNICATION_LOST, event);
        return true;
    }

    /**
     * Notify death.
     *
     * @param timeElapsed the time elapsed
     * @param asynchronousHeartbeat the asynchronous heartbeat
     * @return true, if successful
     */
    private boolean notifyDeath(int timeElapsed, Future asynchronousHeartbeat) {
        HeartbeatEvent event = new HeartbeatEvent(expectedPayload, timeElapsed, new Date().getTime());
        notify(HeartbeatState.HOST_OFFLINE, event);
        asynchronousHeartbeat.cancel(true);
        return true;
    }

    /**
     * Notify heartbeat.
     *
     * @param lossCommAlreadyNotified the loss comm already notified
     * @param timeElapsed the time elapsed
     */
    private void notifyHeartbeat(boolean lossCommAlreadyNotified, int timeElapsed) {
        HeartbeatEvent event = new HeartbeatEvent(data, timeElapsed, new Date().getTime());
        if (lossCommAlreadyNotified)
            notify(HeartbeatState.HOST_ONLINE, event);
        else
            notify(HeartbeatState.HEARTBEAT_RECEIVED, event);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.Observable#notify(java.lang.Object, java.lang.Object)
     */
    @Override
    public void notify(HeartbeatState heartbeatState, HeartbeatEvent event) {
        observer.update(heartbeatState, event);
    }

    /**
     * Receive data.
     *
     * @param datagramSocket socket used to receive from sent data
     * @return received data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private byte[] receiveData(DatagramSocket datagramSocket) throws IOException {
        byte[] receiveData = new byte[UDP_VALID_PACKET_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        datagramSocket.receive(receivePacket);
        receiveData = receivePacket.getData();
        byte[] data = new byte[receivePacket.getLength()];
        System.arraycopy(receiveData, receivePacket.getOffset(), data, 0, receivePacket.getLength());
        return data;
    }
}

