package it.polimi.ingsw.sagrada.network.client;

import java.net.Socket;

public class SocketClient extends Client {
    Socket socket;

    public SocketClient(Socket socket) {
        this.socket = socket;

    }


    //implementare run
    //run deve ascoltare messaggi dal client
}
