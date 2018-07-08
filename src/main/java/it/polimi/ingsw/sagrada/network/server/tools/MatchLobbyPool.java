package it.polimi.ingsw.sagrada.network.server.tools;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class MatchLobbyPool.
 */
public class MatchLobbyPool {

    /** The Constant lobbyPool. */
    private static final List<MatchLobby> lobbyPool = new ArrayList<>();

    /** The Constant lobbyPoolMap. */
    private static final Map<String, MatchLobby> lobbyPoolMap = new HashMap<>();
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER  = Logger.getLogger(MatchLobbyPool.class.getName());

    /**
     * Gets the available lobby.
     *
     * @param username the username
     * @return the available lobby
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public MatchLobby getAvailableLobby(String username) throws IOException {
        MatchLobby availableLobby = null;
        Optional<MatchLobby> findMatch = lobbyPool.stream().filter(lobby -> !lobby.isFull() && (!lobby.isInGame() || lobby.wasHere(username))).findFirst();

        if(findMatch.isPresent())
            availableLobby = findMatch.get();
        if(availableLobby == null) {
            availableLobby = new MatchLobby(DataManager.getSignOut(), lobbyPool.size() + "");
            lobbyPoolMap.put(lobbyPool.size() + "", availableLobby);
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

    /**
     * Release lobby.
     *
     * @param matchLobbyId the match lobby id
     * @return true, if successful
     */
    public boolean releaseLobby(String matchLobbyId) {
        MatchLobby matchLobby = lobbyPoolMap.remove(matchLobbyId);
        if(matchLobby == null)
            return false;
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