package it.polimi.ingsw.sagrada.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private int port;
    private ServerSocket serverSocket;
    private ExecutorService mainExecutor;
    private ExecutorService executor;

    Socket socket;

    Server() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainExecutor = Executors.newSingleThreadExecutor();


    }

    @java.lang.SuppressWarnings("squid:S2189")
    private void startConnection() throws IOException {

        executor = Executors.newCachedThreadPool();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread thread = new Thread(new PlayerThread(clientSocket));
            executor.submit(thread);
        }

    }


}
