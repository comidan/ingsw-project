package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.network.server.tools.PortDiscovery;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.LoginManager.LoginState;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                Map<String, String> requestData = commandParser.parse(action);
                if (requestData != null && requestData.get("type").equals("login")) {
                    LoginState loginState = loginManager.autheanticate(requestData.get("username"), requestData.get("password"));
                    switch (loginState) {
                        case AUTH_OK:
                            lobbyPort = joinUserLobby(requestData.get("username"));
                            LoginManager.sendLoginResponse(clientSocket, requestData.get("username"), lobbyPort);
                            clientSocket.close();
                            break;
                        case AUTH_FAILED_USER_ALREADY_LOGGED:
                            LoginManager.sendLoginError(clientSocket,"User already logged on");
                            break;
                        case AUTH_FAILED_USER_NOT_EXIST:
                            if (loginManager.signUp("", "")) {
                                lobbyPort = joinUserLobby(requestData.get("username"));
                                LoginManager.sendLoginResponse(clientSocket, requestData.get("username"), lobbyPort);
                                clientSocket.close();
                            }
                            else
                                LoginManager.sendLoginError(clientSocket,"Username already taken");
                            break;
                        default: LoginManager.sendLoginError(clientSocket,"Error"); break;
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
