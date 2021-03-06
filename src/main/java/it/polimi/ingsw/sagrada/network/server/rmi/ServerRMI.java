package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI;
import it.polimi.ingsw.sagrada.network.server.Server;
import it.polimi.ingsw.sagrada.network.server.tools.DataManager;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobby;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class ServerRMI.
 */
public class ServerRMI extends UnicastRemoteObject implements AbstractServerRMI, Server {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ServerRMI.class.getName());

    /** The data manager. */
    private DataManager dataManager;
    
    /** The available lobby. */
    private MatchLobby availableLobby;

    /**
     * Instantiates a new server RMI.
     *
     * @param port rmi binding port
     * @throws RemoteException the remote exception
     */
    public ServerRMI(int port) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(port);
            registry.bind("ServerRMI", this);
            initializeCoreFunction();
        }
        catch (AlreadyBoundException exc) {
            LOGGER.log(Level.SEVERE, () -> "Fatal error while initializing rmi server " + exc.getMessage());
        }
    }

    /**
     * Initialize core function.
     */
    private void initializeCoreFunction() {
        dataManager = DataManager.getDataManager();
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Server RMI correctly initialized and running");
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.rmi.AbstractServerRMI#login(it.polimi.ingsw.sagrada.network.client.rmi.ClientRMI, java.lang.String, java.lang.String)
     */
    @Override
    public LoginState login(ClientRMI clientRMI, String username, String hashedPassword) throws RemoteException {
        try {
                LoginState loginState = dataManager.authenticate(username, hashedPassword);
                MatchLobby lobby;
                switch (loginState) {
                    case AUTH_OK:
                        lobby = joinUserLobby(username);
                        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->username + " correctly logged, migrating client to lobby server");
                        return loginState;
                    case AUTH_FAILED_USER_ALREADY_LOGGED:
                        Logger.getLogger(getClass().getName()).log(Level.INFO, () -> loginState +"");
                        return loginState;
                    case AUTH_FAILED_USER_NOT_EXIST:
                        if (dataManager.signUp(username, hashedPassword)) {
                            lobby = joinUserLobby(username);
                            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->username + " correctly signed up, migrating client to lobby server");
                            return LoginState.AUTH_OK;
                        }
                        else {
                            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Sign up failed");
                            return LoginState.AUTH_FATAL_ERROR;
                        }
                    default: Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"No correct command was found"); return LoginState.AUTH_FATAL_ERROR;
                }
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, () -> "login session fatal error");
            return LoginState.AUTH_FATAL_ERROR;
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.server.rmi.AbstractServerRMI#getMatchLobbyId()
     */
    @Override
    public String getMatchLobbyId() throws RemoteException {
        return availableLobby.getLobbyIdentifier();
    }

    /**
     * Join user lobby.
     *
     * @param clientIdToken the client id token
     * @return the match lobby
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private synchronized MatchLobby joinUserLobby(String clientIdToken) throws IOException{
        availableLobby = lobbyPool.getAvailableLobby(clientIdToken);
        availableLobby.addClient(clientIdToken);
        return availableLobby;
    }


}
