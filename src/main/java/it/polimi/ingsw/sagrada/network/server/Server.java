package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.network.utilities.LoginManager;
import it.polimi.ingsw.sagrada.network.client.SocketClient;
import it.polimi.ingsw.sagrada.network.utilities.MatchLobby;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    private SocketClient socketClient;
    private LoginManager loginManager;
    private List<MatchLobby> matchLobbyList;
    Socket socket;


    public void Server() {
        serverSocket = createServerSocket();
        matchLobbyList = new ArrayList<>();

    }


    private ServerSocket createServerSocket() {
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void run() {
        executor = Executors.newSingleThreadExecutor();
        executor.submit(this);

        while (!executor.isShutdown()) {
            try {
                Socket clientSocket = serverSocket.accept();
                socketClient = new SocketClient(clientSocket);
                if (loginManager.checkLogin()) {
                    choseLobby().addClient(socketClient);
                } else {
                    System.out.println("Login Failed");
                }
            } catch (IOException exc) {
                System.out.print("Error");
            }
        }
    }

    private MatchLobby choseLobby() {
        if (!matchLobbyList.isEmpty()) {
            for (MatchLobby matchLobby : matchLobbyList) {
                if (!matchLobby.isFull())
                    return matchLobby;
            }
        }
        MatchLobby newLobby = new MatchLobby();
        matchLobbyList.add(newLobby);
        return newLobby;
    }

}
