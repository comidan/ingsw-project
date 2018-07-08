package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;



/**
 * The Enum HeartbeatState.
 */
public enum HeartbeatState {
    
    /** The host offline. */
    HOST_OFFLINE,
    
    /** The host online. */
    HOST_ONLINE,
    
    /** The heartbeat received. */
    HEARTBEAT_RECEIVED,
    
    /** The communication lost. */
    COMMUNICATION_LOST
}
