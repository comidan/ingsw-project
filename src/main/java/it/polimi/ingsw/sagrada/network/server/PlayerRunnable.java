package it.polimi.ingsw.sagrada.network.server;


import it.polimi.ingsw.sagrada.network.utilities.*;
import org.json.simple.*;

import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Socket;

public class PlayerRunnable implements Runnable {


    private Socket clientSocket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private UserPool userPool;
    private String userName;
    private JSONParser parser = new JSONParser();


    protected PlayerRunnable(Socket clientSocket) {
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
                String message = inSocket.readLine();
                try {
                    JSONObject jsonMessage = (JSONObject) parser.parse(message);
                    String actionType = (String) jsonMessage.get("action");
                    if (actionType.equals("login"))
                        login();

                } catch (org.json.simple.parser.ParseException exc) {

                }


            } catch (IOException exc) {
                exc.printStackTrace();
                try {
                    clientSocket.close();
                } catch (IOException _exc) {

                }
            }
        }
    }

    private boolean login() throws IOException {

        return true;
    }

}
