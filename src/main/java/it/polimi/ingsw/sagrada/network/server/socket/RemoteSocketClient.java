package it.polimi.ingsw.sagrada.network.server.socket;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.DisconnectEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.MessageEvent;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.network.client.ClientBase;
import it.polimi.ingsw.sagrada.network.security.Security;
import it.polimi.ingsw.sagrada.network.server.protocols.application.JsonToMessageConverter;
import it.polimi.ingsw.sagrada.network.server.protocols.application.MessageToJsonConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class RemoteSocketClient.
 */
public class RemoteSocketClient implements ClientBase, Runnable {

    /** The socket. */
    private Socket socket;
    
    /** The input. */
    private BufferedReader input;
    
    /** The output. */
    private PrintWriter output;
    
    /** The command parser. */
    private JsonToMessageConverter jsonToMessageConverter;
    
    /** The message parser. */
    private MessageToJsonConverter messageToJsonConverter;
    
    /** The executor. */
    private ExecutorService executor;
    
    /** The disconnect. */
    private BiFunction disconnect;
    
    /** The fast recovery. */
    private Function fastRecovery;
    
    /** The send to model. */
    private Consumer sendToModel;
    
    /** The identifier. */
    private String identifier;

    /** The is in fast recovery. */
    private boolean isInFastRecovery;

    /**
     * Instantiates a new remote socket client.
     *
     * @param socket the socket
     * @param identifier the identifier
     * @param disconnect the disconnect
     * @param fastRecovery the fast recovery
     * @param sendToModel the send to model
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public RemoteSocketClient(Socket socket, String identifier, BiFunction disconnect, Function fastRecovery, Consumer sendToModel) throws IOException {
        this.socket = socket;
        isInFastRecovery = false;
        jsonToMessageConverter = new JsonToMessageConverter();
        messageToJsonConverter = new MessageToJsonConverter();
        executor = Executors.newSingleThreadExecutor();
        this.disconnect = disconnect;
        this.fastRecovery = fastRecovery;
        this.identifier = identifier;
        this.sendToModel = sendToModel;
        initCoreFunctions();
    }

    /**
     * Inits the core functions.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void initCoreFunctions() throws IOException{
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);
        executor.submit(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendMessage(java.lang.String)
     */
    @Override
    public void sendMessage(String message) {
        String payload = jsonToMessageConverter.crateJSONMessage(message);
        output.println(Security.getEncryptedData(payload));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendRemoteMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendRemoteMessage(Message message) {
        throw new UnsupportedOperationException("Method not supported");
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#disconnect()
     */
    @Override
    public void disconnect() {
        disconnect.apply(identifier, true);
        close();
        executor.shutdown();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.ClientBase#setTimer(java.lang.String)
     */
    @Override
    public void setTimer(String time) {
        String payload = jsonToMessageConverter.createJSONCountdown(time);
        output.println(Security.getEncryptedData(payload));
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Sending time...");
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#startHeartbeat(int)
     */
    @Override
    public void startHeartbeat(int port) {

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.ClientBase#setPlayer(java.lang.String)
     */
    @Override
    public void setPlayer(String playerName, int position) {
        String payload = jsonToMessageConverter.createJSONAddLobbyPlayer(playerName, position);
        output.println(Security.getEncryptedData(payload));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.ClientBase#removePlayer(java.lang.String)
     */
    @Override
    public void removePlayer(String playerName) {
        String payload = jsonToMessageConverter.createJSONRemoveLobbyPlayer(playerName);
        output.println(Security.getEncryptedData(payload));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#getId()
     */
    @Override
    public String getId() {
        return identifier;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#sendResponse(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendResponse(Message message) {
        String payload = messageToJsonConverter.createJsonResponse((ResponseVisitor) message);
        output.println(Security.getEncryptedData(payload));
    }

    /**
     * Execute payload.
     *
     * @param json the json
     */
    private void executePayload(String json) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->"Receiving : " + json);
        Message parsedMessage = jsonToMessageConverter.parse(json);
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->parsedMessage.getType().getName());
        if(parsedMessage instanceof DisconnectEvent) {
            disconnect();
        }
        else if(parsedMessage instanceof MessageEvent){
            notifyMessage(((MessageEvent) parsedMessage).getMessage());
        }
        else {
            Logger.getLogger(getClass().getName()).log(Level.INFO, () ->parsedMessage + "");
            sendToModel(parsedMessage);
        }
    }

    /**
     * Send to model.
     *
     * @param message the message
     */
    public void sendToModel(Message message) {
        sendToModel.accept(message);
    }

    /**
     * Notify message.
     *
     * @param message the message
     */
    private void notifyMessage(String message) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, () ->message);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#isInFastRecovery()
     */
    @Override
    public boolean isInFastRecovery() {
        return isInFastRecovery;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#setActive(boolean)
     */
    @Override
    public void setActive(boolean active) throws RemoteException {

    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.network.client.Client#close()
     */
    @Override
    public void close() {
        try {
            socket.close();
            executor.shutdown();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e::getMessage);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex::getMessage);
            }
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while (!executor.isShutdown()) {
            try {
                executePayload(Security.getDecryptedData(input.readLine()));
            } catch (IOException exc) {
                isInFastRecovery = true;
                fastRecovery.apply(identifier);
                executor.shutdown();
                close();
            }
        }
    }
}