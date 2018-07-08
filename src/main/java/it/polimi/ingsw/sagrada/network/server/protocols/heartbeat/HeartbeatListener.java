package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;



/**
 * The listener interface for receiving heartbeat events.
 * The class that is interested in processing a heartbeat
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addHeartbeatListener<code> method. When
 * the heartbeat event occurs, that object's appropriate
 * method is invoked.
 *
 * @see HeartbeatEvent
 */
public interface HeartbeatListener {

    /**
     * On heartbeat.
     *
     * @param event the event
     */
    void onHeartbeat(HeartbeatEvent event);

    /**
     * On death.
     *
     * @param event the event
     */
    void onDeath(HeartbeatEvent event);

    /**
     * On loss communication.
     *
     * @param event the event
     */
    void onLossCommunication(HeartbeatEvent event);

    /**
     * On reacquired communication.
     *
     * @param event the event
     */
    void onReacquiredCommunication(HeartbeatEvent event);

    /**
     * On acquired communication.
     *
     * @param event the event
     */
    void onAcquiredCommunication(HeartbeatEvent event);
}
