package it.polimi.ingsw.sagrada.network.server.socket;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.LoginEvent;
import it.polimi.ingsw.sagrada.network.LoginState;
import it.polimi.ingsw.sagrada.network.server.Server;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.tools.DataManager;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobby;
import it.polimi.ingsw.sagrada.network.server.tools.PortDiscovery;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer implements Runnable, Server {

    private static final Logger LOGGER = Logger.getLogger(SocketServer.class.getName());

    private int port;
    private ServerSocket serverSocket;
    private ExecutorService executor, cachedExecutor;
    private CommandParser commandParser;
    private PortDiscovery portDiscovery;
    private DataManager dataManager;

    public SocketServer() throws InterruptedException, ExecutionException, SocketException {
        portDiscovery = new PortDiscovery();
        dataManager = DataManager.getDataManager();
        Future<Integer> discoveringPort = portDiscovery.obtainAvailablePortOnTCPAsync();
        commandParser = new CommandParser();
        port = discoveringPort.get();
        if((serverSocket = createServerSocket()) == null)
            throw new SocketException("Could not create server");
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
                String action = dataManager.receiveLoginData(clientSocket);
                Message requestData = commandParser.parse(action);
                if (requestData instanceof LoginEvent) {
                    LoginEvent loginEvent = (LoginEvent)requestData;
                    LoginState loginState = dataManager.authenticate(loginEvent.getUsername(), loginEvent.getPassword());
                    switch (loginState) {
                        case AUTH_OK:
                            lobbyPort = joinUserLobby(loginEvent.getUsername());
                            DataManager.sendLoginResponse(clientSocket, loginEvent.getUsername(), lobbyPort);
                            System.out.println(loginEvent.getUsername() + " correctly logged, migrating client to lobby server");
                            clientSocket.close();
                            break;
                        case AUTH_FAILED_USER_ALREADY_LOGGED:
                            DataManager.sendLoginError(clientSocket,"User already logged on");
                            System.out.println(loginState);
                            break;
                        case AUTH_FAILED_USER_NOT_EXIST:
                            DataManager.sendLoginSignup(clientSocket);
                            if (dataManager.signUp(loginEvent.getUsername(), loginEvent.getPassword())) {
                                lobbyPort = joinUserLobby(loginEvent.getUsername());
                                DataManager.sendLoginResponse(clientSocket, loginEvent.getUsername(), lobbyPort);
                                System.out.println(loginEvent.getUsername() + "correctly signed up, migrating client to lobby server");
                                clientSocket.close();
                            }
                            else {
                                System.out.println("Wrong password");
                                DataManager.sendLoginError(clientSocket);
                            }
                            break;
                        default: System.out.println("No correct command was found"); DataManager.sendLoginError(clientSocket); break;
                    }
                }
            }
            catch (IOException exc) {
                LOGGER.log(Level.SEVERE, () -> "login session fatal error");
            }
        };
    }

    private synchronized int joinUserLobby(String clientIdToken) throws IOException{
        MatchLobby availableLobby = lobbyPool.getAvailableLobby();
        availableLobby.addClient(clientIdToken);
        return availableLobby.getPort();
    }
}
