package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.LoginEvent;
import it.polimi.ingsw.sagrada.network.server.tools.PortDiscovery;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.LoginManager.LoginState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private int port;
    private ServerSocket serverSocket;
    private ExecutorService executor, cachedExecutor;
    private LoginManager loginManager;
    private CommandParser commandParser;
    private List<MatchLobby> matchLobbyList;
    private PortDiscovery portDiscovery;

    public Server() throws InterruptedException, ExecutionException, SQLException {
        portDiscovery = new PortDiscovery();
        Future<Integer> discoveringPort = portDiscovery.obtainAvailablePortOnTCPAsync();
        matchLobbyList = new ArrayList<>();
        commandParser = new CommandParser();
        loginManager = new LoginManager();
        port = discoveringPort.get();
        serverSocket = createServerSocket();
        initializeCoreFunction();
    }


    private void initializeCoreFunction() {
        executor = Executors.newSingleThreadExecutor();
        cachedExecutor = Executors.newCachedThreadPool();
        executor.submit(this);
        System.out.println("Server correctly initialized and running on port " + port);
    }

    private ServerSocket createServerSocket() {
        try {

            return new ServerSocket(port);
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, () -> "server init error");
            return null;
        }
    }

    @Override
    public void run() {
        while (!executor.isShutdown()) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Received new login request");
                cachedExecutor.submit(initializeUserSession(clientSocket));
            }
            catch (IOException exc) {
                LOGGER.log(Level.SEVERE, () -> "server accept error");
            }
        }
    }

    private Runnable initializeUserSession(Socket clientSocket) {
        return () -> {
            try {
                int lobbyPort;
                String action = loginManager.receiveLoginData(clientSocket);
                Message requestData = commandParser.parse(action);
                if (requestData instanceof LoginEvent) {
                    System.out.println("Parsing received json login request");
                    LoginEvent loginEvent = (LoginEvent)requestData;
                    LoginState loginState = loginManager.authenticate(loginEvent.getUsername(), loginEvent.getPassword());
                    System.out.println(loginEvent.getUsername() + " " + loginEvent.getPassword());
                    switch (loginState) {
                        case AUTH_OK:
                            lobbyPort = joinUserLobby(loginEvent.getUsername());
                            LoginManager.sendLoginResponse(clientSocket, loginEvent.getUsername(), lobbyPort);
                            System.out.println("Correctly logged, migrating client to lobby server");
                            clientSocket.close();
                            break;
                        case AUTH_FAILED_USER_ALREADY_LOGGED:
                            LoginManager.sendLoginError(clientSocket,"User already logged on");
                            System.out.println("Login error");
                            break;
                        case AUTH_FAILED_USER_NOT_EXIST:
                            LoginManager.sendLoginSignup(clientSocket);
                            if (loginManager.signUp("", "")) {
                                lobbyPort = joinUserLobby(loginEvent.getUsername());
                                LoginManager.sendLoginResponse(clientSocket, loginEvent.getUsername(), lobbyPort);
                                System.out.println("Correctly signed up, migrating client to lobby server");
                                clientSocket.close();
                            }
                            else {
                                System.out.println("Login error");
                                LoginManager.sendLoginError(clientSocket);
                            }
                            break;
                        default: System.out.println("No correct command was found"); LoginManager.sendLoginError(clientSocket); break;
                    }
                }
            }
            catch (IOException exc) {
                LOGGER.log(Level.SEVERE, () -> "login session fatal error");
            }
        };
    }

    private synchronized int joinUserLobby(String clientIdToken) throws IOException{
        MatchLobby availableLobby = null;
        for(MatchLobby lobby : matchLobbyList)
            if(!lobby.isFull()) {
                availableLobby = lobby;
                break;
            }
        if(availableLobby == null) {
            availableLobby = new MatchLobby(loginManager.getSignOut());
            matchLobbyList.add(availableLobby);
        }
        availableLobby.addClient(clientIdToken);
        return availableLobby.getPort();
    }
}
