package it.polimi.ingsw.sagrada.network.server.rmi;

import it.polimi.ingsw.sagrada.network.server.Server;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.tools.LoginManager;
import it.polimi.ingsw.sagrada.network.server.tools.LoginManager.LoginState;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobby;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMI extends UnicastRemoteObject implements AbstractServerRMI, Server, Runnable {

    private static final Logger LOGGER = Logger.getLogger(ServerRMI.class.getName());

    private ExecutorService executor, cachedExecutor;
    private CommandParser commandParser;
    private LoginManager loginManager;

    public ServerRMI() throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.bind("ServerRMI", this);
            commandParser = new CommandParser();
            initializeCoreFunction();
        }
        catch (AlreadyBoundException exc) {
            LOGGER.log(Level.SEVERE, () -> "Fatal error while initializing rmi server " + exc.getMessage());
        }
    }

    private void initializeCoreFunction() {
        loginManager = LoginManager.getLoginManager();
        executor = Executors.newSingleThreadExecutor();
        cachedExecutor = Executors.newCachedThreadPool();
        executor.submit(this);
        System.out.println("Server RMI correctly initialized and running");
    }

    public LoginState login(ClientRMI clientRMI, String username, String hashedPassword) {
        try {
                LoginState loginState = loginManager.authenticate(username, hashedPassword);
                MatchLobby lobby;
                switch (loginState) {
                    case AUTH_OK:
                        lobby = joinUserLobby(username);
                        System.out.println(username + " correctly logged, migrating client to lobby server");
                        clientRMI.notifyLobby(lobby);
                        return loginState;
                    case AUTH_FAILED_USER_ALREADY_LOGGED:
                        System.out.println(loginState);
                        return loginState;
                    case AUTH_FAILED_USER_NOT_EXIST:
                        clientRMI.signUp();
                        if (loginManager.signUp(username, hashedPassword)) {
                            lobby = joinUserLobby(username);
                            System.out.println(username + " correctly signed up, migrating client to lobby server");
                            clientRMI.notifyLobby(lobby);
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

    private synchronized MatchLobby joinUserLobby(String clientIdToken) throws IOException{
        MatchLobby availableLobby = lobbyPool.getAvailableLobby();
        availableLobby.addClient(clientIdToken);
        return availableLobby;
    }

    @Override
    public void run() {

    }
}
