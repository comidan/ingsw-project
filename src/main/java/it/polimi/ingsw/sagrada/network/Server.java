package it.polimi.ingsw.sagrada.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server implements Runnable {
    private int port;
    private ServerSocket serverSocket;
    private ExecutorService mainExecutor;
    private ExecutorService executor;
    private SocketClient socketClient;
    private LoginManager loginManager;

    Socket socket;


//move to private method called in constructor
    Server() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        mainExecutor = Executors.newSingleThreadExecutor();
        mainExecutor.submit(this);
    //    executor = Executors.newCachedThreadPool();

        while (!mainExecutor.isShutdown()) {
            try {
                Socket clientSocket = serverSocket.accept();
                socketClient= new SocketClient(clientSocket);
                if(loginManager.checkLogin()) {

                }

                else System.out.println("Login Failed");

            }
            catch(IOException exc){

            }

        }

    }


    //deve controllare se matchlobby e piena, se lo e ne crea un'altra

}
