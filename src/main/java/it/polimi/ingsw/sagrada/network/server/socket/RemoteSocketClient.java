package it.polimi.ingsw.sagrada.network.server.socket;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.DisconnectEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.MessageEvent;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class RemoteSocketClient implements Client, Runnable {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private CommandParser commandParser;
    private ExecutorService executor;
    private Function disconnect;
    private Function fastRecovery;
    private String identifier;

    public RemoteSocketClient(Socket socket, String identifier, Function disconnect, Function fastRecovery) throws IOException {
        this.socket = socket;
        commandParser = new CommandParser();
        executor = Executors.newSingleThreadExecutor();
        this.disconnect = disconnect;
        this.fastRecovery = fastRecovery;
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

    @Override
    public void disconnect() throws RemoteException {
        disconnect.apply(identifier);
        close();
        executor.shutdown();
    }

    private void executePayload(String json) throws RemoteException{
        Message parsedMessage = commandParser.parse(json);
        if(parsedMessage instanceof DisconnectEvent) {
            disconnect();
        }
        else if(parsedMessage instanceof MessageEvent)
            notifyMessage(((MessageEvent) parsedMessage).getMessage());
    }

    private void notifyMessage(String message) {
        System.out.println(message);
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
                fastRecovery.apply(identifier);
                executor.shutdown();
                close();
            }
        }
    }
}
