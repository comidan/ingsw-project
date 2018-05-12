package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;

public interface HeartbeatListener {

    void onHeartbeat(HeartbeatEvent event);

    void onDeath(HeartbeatEvent event);

    void onLossCommunication(HeartbeatEvent event);

    void onReacquiredCommunication(HeartbeatEvent event);

    void onAcquiredCommunication(HeartbeatEvent event);
}
