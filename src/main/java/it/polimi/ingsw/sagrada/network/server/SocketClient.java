package it.polimi.ingsw.sagrada.network.server;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.DisconnectEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.MessageEvent;
import it.polimi.ingsw.sagrada.network.client.JsonMessage;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class SocketClient implements Client {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private JsonMessage loginMessage;
    private CommandParser commandParser;
    private ExecutorService executor;
    private Function disconnect;
    private String identifier;

    public SocketClient(Socket socket, String identifier, Function disconnect) throws IOException {
        this.socket = socket;
        commandParser = new CommandParser();
        executor = Executors.newSingleThreadExecutor();
        this.disconnect = disconnect;
        this.identifier = identifier;
        initCoreFunctions();
    }

    private void initCoreFunctions() throws IOException{
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        executor.submit(this);
    }

    @Override
    public void sendMessage(String message) {
        String payload = commandParser.crateJSONMessage(message);
        output.println(payload);
        output.flush();
    }

    @Override
    public void doActions() {

    }

    private void executePayload(String json) {
        Message parsedMessage = commandParser.parse(json);
        if(parsedMessage instanceof DisconnectEvent) {
            disconnect.apply(identifier);
            close();
            executor.shutdown();
        }
        else if(parsedMessage instanceof MessageEvent)
            System.out.println(((MessageEvent) parsedMessage).getMessage());
    }

    @Override
    public void close() {
        try {
            socket.close();
            executor.shutdown();
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
        while (!executor.isShutdown()) {
            try {
                executePayload(input.readLine());
            } catch (IOException exc) {

            }
        }
    }
}
