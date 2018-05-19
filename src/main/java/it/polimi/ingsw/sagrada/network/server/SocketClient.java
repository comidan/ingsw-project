package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.network.client.JsonMessage;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;

public class SocketClient implements Client {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private JsonMessage loginMessage;
    private CommandParser commandParser;

    public SocketClient(Socket socket) throws IOException {
        this.socket = socket;
        commandParser = new CommandParser();
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
    }

    private JSONObject createMessage(String userName, String auth) {
        loginMessage = new JsonMessage();
        return loginMessage.createLoginMessage(userName, auth);
    }

    void sendMessage(String payload) {

    }

    @Override
    public void doActions() {

    }

    @Override
    public void close() {
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

    @Override
    public void run() {

    }
}
