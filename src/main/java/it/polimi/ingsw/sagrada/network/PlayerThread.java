package it.polimi.ingsw.sagrada.network;


import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;

public class PlayerThread implements Runnable {


    private Socket clientSocket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private UserPool userPool;
    private String userName;


    public PlayerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            inSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }

    @java.lang.SuppressWarnings("squid:S2189")
    @Override
    public void run() {
        while (true) {
            try {
                String request = inSocket.readLine();
                if (request.equals("LOGIN"))
                    login();
            } catch (IOException exc) {
                exc.printStackTrace();
                try {
                    clientSocket.close();
                } catch (IOException _exc) {

                }
                return;

            }
        }
    }

    private boolean login() throws IOException {
        userName = inSocket.readLine();
        if (userPool.addUser(userName)) ;
        outSocket.flush();
        return true;
    }

}
