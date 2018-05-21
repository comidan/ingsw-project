package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.network.server.tools.LoginManager;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobbyPool;

public interface Server {

    MatchLobbyPool lobbyPool = new MatchLobbyPool();
}
