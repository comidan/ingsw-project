package it.polimi.ingsw.sagrada.network.server;


import it.polimi.ingsw.sagrada.network.utilities.*;
import org.json.simple.*;


import java.io.*;
import java.net.Socket;

import static java.lang.Boolean.*;

public class PlayerRunnable implements Runnable {


    private Socket clientSocket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private String userName;
    private CommandParser commandParser;


    protected PlayerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
        commandParser = new CommandParser();
        try {
            inSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
        } catch (IOException exc) {
            exc.printStackTrace();
        }

    }

    @Override
    public void run() {
        boolean exit = FALSE;
        while (exit == FALSE) {
            try {
                String message = inSocket.readLine();
                ActionEnum action = commandParser.parse(message);
                switch (action) {
                    case LOGIN:
                        login();
                        break;
                    case END:
                        exit = TRUE;
                        break;
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
