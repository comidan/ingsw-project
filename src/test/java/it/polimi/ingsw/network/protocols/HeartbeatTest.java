package it.polimi.ingsw.network.protocols;

import it.polimi.ingsw.sagrada.network.client.protocols.heartbeat.HeartbeatProtocolManager;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatEvent;
import it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatListener;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import java.io.IOException;

public class HeartbeatTest implements HeartbeatListener {

    @Test
    public void testHeartbeatFunctionality() throws IOException, InterruptedException {
        int port = 9876;
        String host = System.getProperty("myapplication.ip");
        HeartbeatProtocolManager heartbeatProtocolManagerClient = new HeartbeatProtocolManager(host, port);
        it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatProtocolManager heartbeatProtocolManagerServer
                = new it.polimi.ingsw.sagrada.network.server.protocols.heartbeat.HeartbeatProtocolManager(this, port);
        Thread.sleep(5000);
    }

    @Override
    public void onHeartbeat(HeartbeatEvent event) {
        assertNotNull(event);
        System.out.println("Received heartbet from " + event.getSource() + " in " + event.getTimeFromPreviousBeat() + " at " + event.getBeatTimeStamp());
    }

    @Override
    public void onDeath(HeartbeatEvent event) {
        assertNotNull(event);
        System.out.println(event.getSource() + " died after " + event.getTimeFromPreviousBeat() + " at " + event.getBeatTimeStamp());
    }

    @Override
    public void onLossCommunication(HeartbeatEvent event) {
        assertNotNull(event);
        System.out.println("Communication lost of " + event.getSource() + " in " + event.getTimeFromPreviousBeat() + " at " + event.getBeatTimeStamp());
    }

    @Override
    public void onReacquiredCommunication(HeartbeatEvent event) {
        assertNotNull(event);
        System.out.println("Communication reacquired of " + event.getSource() + " in " + event.getTimeFromPreviousBeat() + " at " + event.getBeatTimeStamp());
    }

    @Override
    public void onAcquiredCommunication(HeartbeatEvent event) {
        assertNotNull(event);
        System.out.println(event.getSource() + " connected at " + event.getBeatTimeStamp());
    }
}
