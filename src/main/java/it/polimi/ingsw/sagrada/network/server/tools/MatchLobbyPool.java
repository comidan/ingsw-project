package it.polimi.ingsw.sagrada.network.server.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchLobbyPool {

    private static final List<MatchLobby> lobbyPool = new ArrayList<>();

    public MatchLobby getAvailableLobby() throws IOException {
        MatchLobby availableLobby = null;
        for(MatchLobby lobby : lobbyPool)
            if(!lobby.isFull()) {
                availableLobby = lobby;
                break;
            }
        if(availableLobby == null) {
            availableLobby = new MatchLobby(LoginManager.getSignOut(), lobbyPool.size() + "");
            lobbyPool.add(availableLobby);
        }
        return availableLobby;
    }

    public boolean releaseLobby(MatchLobby matchLobby) {
        matchLobby.closeLobby();
        return lobbyPool.remove(matchLobby);
    }
}
