package it.polimi.ingsw.sagrada.network.server.socket;

import it.polimi.ingsw.sagrada.game.intercomm.EventTypeEnum;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.server.protocols.application.CommandParser;
import it.polimi.ingsw.sagrada.network.server.protocols.application.MessageParser;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

public class RemoteSocketClient implements Client, Runnable {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private CommandParser commandParser;
    private MessageParser messageParser;
    private ExecutorService executor;
    private Function disconnect;
    private Function fastRecovery;
    private Consumer sendToModel;
    private String identifier;

    public RemoteSocketClient(Socket socket, String identifier, Function disconnect, Function fastRecovery, Consumer sendToModel) throws IOException {
        this.socket = socket;
        commandParser = new CommandParser();
        messageParser = new MessageParser();
        executor = Executors.newSingleThreadExecutor();
        this.disconnect = disconnect;
        this.fastRecovery = fastRecovery;
        this.identifier = identifier;
        this.sendToModel = sendToModel;
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
    public void disconnect() {
        disconnect.apply(identifier);
        close();
        executor.shutdown();
    }

    @Override
    public void setTimer(String time) {
        String payload = commandParser.createJSONCountdown(time);
        output.println(payload);
        output.flush();
        System.out.println("Sending time...");
    }

    @Override
    public void setPlayer(String playerName) {
        String payload = commandParser.createJSONAddLobbyPlayer(playerName);
        System.out.println("Sending player data...");
        output.println(payload);
        output.flush();
        System.out.println("Sent");
    }

    @Override
    public void removePlayer(String playerName) {
        String payload = commandParser.createJSONRemoveLobbyPlayer(playerName);
        output.println(payload);
        output.flush();
    }

    @Override
    public String getId() {
        return identifier;
    }

    @Override
    public void sendResponse(Message message) {
        //manda i messaggi del model al client
        String messageType = message.getType().getName();
        String payload;
        if(messageType.equals(EventTypeEnum.toString(EventTypeEnum.DICE_RESPONSE)))
            payload = messageParser.createJsonDiceResponse((DiceResponse)message);
        else if(messageType.equals(EventTypeEnum.toString(EventTypeEnum.WINDOW_RESPONSE)))
            payload = messageParser.createJsonWindowResponse((WindowResponse)message);
        else if(messageType.equals(EventTypeEnum.toString(EventTypeEnum.BEGIN_TURN_EVENT)))
            payload = messageParser.createJsonBeginTurnEvent((BeginTurnEvent)message);
        else
            payload = "ERROR";
        output.println(payload);
        output.flush();
    }

    private void executePayload(String json) {
        Message parsedMessage = commandParser.parse(json);
        if(parsedMessage instanceof DisconnectEvent) {
            disconnect();
        }
        else if(parsedMessage instanceof MessageEvent)
            notifyMessage(((MessageEvent) parsedMessage).getMessage());
        else {
            System.out.println("Sending to model...");
            sendToModel(parsedMessage);
        }
    }

    public void sendToModel(Message message) {
        sendToModel.accept(message);
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
