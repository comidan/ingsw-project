package it.polimi.ingsw.sagrada.network.client;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {

    int SERVER_WAITING_RESPONSE_TIME = 3000;

    String NETWORK_CONFIG_PATH = "src/main/resources/json/config/network_config.json";

    void doActions() throws RemoteException;

    void sendMessage(String message) throws RemoteException;

    void close() throws RemoteException;

    void disconnect() throws RemoteException;

    void setTimer(String time) throws RemoteException;

    void setPlayer(String playerName) throws RemoteException;

    void removePlayer(String playerName) throws RemoteException;

    String getId() throws RemoteException;

    void sendResponse(Message message) throws RemoteException;
}