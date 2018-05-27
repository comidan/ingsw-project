package it.polimi.ingsw.sagrada.network.client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Level;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public interface Client extends Remote {

    int SERVER_WAITING_RESPONSE_TIME = 3000;

    String NETWORK_CONFIG_PATH = "src/main/resources/json/config/network_config.json";

    void doActions() throws RemoteException;

    void sendMessage(String message) throws RemoteException;

    void close() throws RemoteException;

    void disconnect() throws RemoteException;
}