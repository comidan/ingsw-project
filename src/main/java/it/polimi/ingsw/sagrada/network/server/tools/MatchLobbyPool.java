package it.polimi.ingsw.sagrada.network.server.tools;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchLobbyPool {

    private static final List<MatchLobby> lobbyPool = new ArrayList<>();
    private static final Logger LOGGER  = Logger.getLogger(MatchLobbyPool.class.getName());

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
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.unbind(matchLobby.getLobbyIdentifier());
        }
        catch (RemoteException|NotBoundException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
        }
        return lobbyPool.remove(matchLobby);
    }
}
