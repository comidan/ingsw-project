package it.polimi.ingsw.sagrada.network;

import org.json.simple.JSONObject;

import java.net.*;
import java.io.*;

public class Client {

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;
    private JsonMessage loginMessage;

    private final static int PORT = 3000;
    private final static String address = "localhost";

    public Client() {

        try {
            doActions();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JSONObject createMessage(String userName) {
        loginMessage = new JsonMessage();
        return loginMessage.createMessage(userName);
    }

    private void doActions() {
        try {
            connect();
            login();
            close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Always close it:
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }

    private void close() {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }
    }

    private void login() {
        try {
            boolean loginSuccessful = false;


            while (!loginSuccessful) {
                outVideo.println("username:");
                String username = inKeyboard.readLine();
                JSONObject message = createMessage(username);
                outSocket.println(message);
                outSocket.flush();
                loginSuccessful = Boolean.valueOf(inSocket.readLine()).booleanValue();
            }
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.err.println("Socket not closed");
            }
        }
    }

}