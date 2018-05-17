package it.polimi.ingsw.sagrada.network.utilities;

import it.polimi.ingsw.sagrada.network.client.SocketClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MatchLobby {

    List<SocketClient> socketClientList;
    private final static int FULL_SIZE = 4;
    private ExecutorService executor;

    public MatchLobby() {
        socketClientList = new ArrayList<>();
        executor = Executors.newCachedThreadPool();
    }

    public boolean isFull() {
        return (socketClientList.size() == FULL_SIZE);
    }

    public void addClient(SocketClient socketClient) {
        socketClientList.add(socketClient);
        executor.submit(socketClient);
    }


    // deve implementare listener del protocollo heartbeat
}
