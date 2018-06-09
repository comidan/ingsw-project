package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;

public class LobbyLoginEvent implements Message {

    private String token;
    private int lobbyPort;

    public LobbyLoginEvent(String token, int lobbyPort) {
        this.token = token;
        this.lobbyPort = lobbyPort;
    }

    public String getToken() {
        return token;
    }

    public int getLobbyPort() {
        return lobbyPort;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
