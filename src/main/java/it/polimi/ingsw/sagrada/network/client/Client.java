package it.polimi.ingsw.sagrada.network.client;

import it.polimi.ingsw.sagrada.network.JsonMessage;
import org.json.simple.JSONObject;

import java.net.*;
import java.io.*;

public class Client implements Runnable {

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;
    private JsonMessage loginMessage;

    private static final int PORT = 3000;
    private static final String address = "localhost";

    public Client() {

        try {
            doActions();
        } catch (Exception e) {
        }
    }

    private JSONObject createMessage(String userName, String auth) {
        loginMessage = new JsonMessage();
        return loginMessage.createLoginMessage(userName, auth);
    }

    private void doActions() {
        try {
            connect();
            login();
            close();
        } catch (Exception e) {
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    private void connect() {
        try {
            socket = new Socket(address, PORT);
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            inKeyboard = new BufferedReader(new InputStreamReader(System.in));
            outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);

        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    private void close() {
        try {
            socket.close();
        } catch (Exception e) {
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {

            }
        }
    }

    private void login() {
        try {
            boolean loginSuccessful = false;


            while (!loginSuccessful) {
                outVideo.println("username:");
                String username = inKeyboard.readLine();
                outVideo.println("password:");
                String auth = inKeyboard.readLine();
                JSONObject message = createMessage(username, auth);
                outSocket.println(message);
                outSocket.flush();
                loginSuccessful = Boolean.valueOf(inSocket.readLine()).booleanValue();
            }
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
    }

    public void run() {

    }

}