package it.polimi.ingsw.sagrada.network.server.tools;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class MatchLobbyPool.
 */
public class MatchLobbyPool {

    /** The Constant lobbyPool. */
    private static final List<MatchLobby> lobbyPool = new ArrayList<>();
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER  = Logger.getLogger(MatchLobbyPool.class.getName());

    /**
     * Gets the available lobby.
     *
     * @return the available lobby
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public MatchLobby getAvailableLobby(String username) throws IOException {
        MatchLobby availableLobby = null;
        Optional<MatchLobby> findMatch = lobbyPool.stream().findAny().filter(lobby -> !lobby.isFull()).filter(lobby -> !lobby.isInGame() || lobby.wasHere(username));
        if(findMatch.isPresent())
            availableLobby = findMatch.get();
        if(availableLobby == null) {
            availableLobby = new MatchLobby(DataManager.getSignOut(), lobbyPool.size() + "");
            lobbyPool.add(availableLobby);
        }
        return availableLobby;
    }

    /**
     * Release lobby.
     *
     * @param matchLobby the match lobby
     * @return true, if successful
     */
    public boolean releaseLobby(MatchLobby matchLobby) {
        matchLobby.closeLobby();
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.unbind(matchLobby.getLobbyIdentifier());
        }
        catch (RemoteException|NotBoundException|NullPointerException exc) {
            LOGGER.log(Level.SEVERE, exc.getMessage());
        }
        return lobbyPool.remove(matchLobby);
    }
}