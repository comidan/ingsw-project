package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.network.server.tools.MatchLobbyPool;



/**
 * The Interface Server.
 */
public interface Server {

    /** The lobby pool. */
    MatchLobbyPool lobbyPool = new MatchLobbyPool();
}
