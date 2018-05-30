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

public class ServerRMI extends UnicastRemoteObject implements AbstractServerRMI, Server {

    private static final Logger LOGGER = Logger.getLogger(ServerRMI.class.getName());

    private DataManager dataManager;
    private MatchLobby availableLobby;

    public ServerRMI() throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind("ServerRMI", this);
            initializeCoreFunction();
        }
        catch (AlreadyBoundException exc) {
            LOGGER.log(Level.SEVERE, () -> "Fatal error while initializing rmi server " + exc.getMessage());
        }
    }

    private void initializeCoreFunction() {
        dataManager = DataManager.getDataManager();
        System.out.println("Server RMI correctly initialized and running");
    }

    @Override
    public LoginState login(ClientRMI clientRMI, String username, String hashedPassword) throws RemoteException {
        try {
                LoginState loginState = dataManager.authenticate(username, hashedPassword);
                MatchLobby lobby;
                switch (loginState) {
                    case AUTH_OK:
                        lobby = joinUserLobby(username);
                        //clientRMI.notifyLobby(lobby.getLobbyIdentifier());
                        System.out.println(username + " correctly logged, migrating client to lobby server");
                        return loginState;
                    case AUTH_FAILED_USER_ALREADY_LOGGED:
                        System.out.println(loginState);
                        return loginState;
                    case AUTH_FAILED_USER_NOT_EXIST:
                        //clientRMI.signUp();
                        if (dataManager.signUp(username, hashedPassword)) {
                            //clientRMI.signUp();
                            lobby = joinUserLobby(username);
                            System.out.println(username + " correctly signed up, migrating client to lobby server");
                            //clientRMI.notifyLobby(lobby.getLobbyIdentifier());
                            return LoginState.AUTH_OK;
                        }
                        else {
                            System.out.println("Sign up failed");
                            return LoginState.AUTH_FATAL_ERROR;
                        }
                    default: System.out.println("No correct command was found"); return LoginState.AUTH_FATAL_ERROR;
                }
        }
        catch (IOException exc) {
            LOGGER.log(Level.SEVERE, () -> "login session fatal error");
            return LoginState.AUTH_FATAL_ERROR;
        }
    }

    @Override
    public String getMatchLobbyId() throws RemoteException {
        System.out.println("Sending matchLobby");
        return availableLobby.getLobbyIdentifier();
    }

    private synchronized MatchLobby joinUserLobby(String clientIdToken) throws IOException{
        availableLobby = lobbyPool.getAvailableLobby();
        availableLobby.addClient(clientIdToken);
        return availableLobby;
    }


}
