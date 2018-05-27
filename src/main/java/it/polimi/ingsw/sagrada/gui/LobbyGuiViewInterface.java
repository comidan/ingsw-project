package it.polimi.ingsw.sagrada.gui;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LobbyGuiViewInterface extends Remote {
    void setFirstPlayer(String message) throws RemoteException;
    void setSecondPlayer(String message) throws RemoteException;
    void setThirdPlayer(String message) throws RemoteException;
    void setFourthPlayer(String message) throws RemoteException;
    void setTimer(String message) throws RemoteException;
}
