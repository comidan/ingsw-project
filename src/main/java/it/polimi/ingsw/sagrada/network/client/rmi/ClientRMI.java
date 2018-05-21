package it.polimi.ingsw.sagrada.network.client.rmi;

import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.server.tools.MatchLobby;

import java.rmi.Remote;

public interface ClientRMI extends Client, Remote {

    void notifyLobby(MatchLobby matchLobby);

    void signUp();

    void notifyHeartbeatPort(Integer port);
}
