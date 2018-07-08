package it.polimi.ingsw.sagrada.game.intercomm.message.lobby;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;



/**
 * The Class LobbyLoginEvent.
 */
public class LobbyLoginEvent implements Message {

    /** The token. */
    private String token;
    
    /** The lobby port. */
    private int lobbyPort;

    /**
     * Instantiates a new lobby login event.
     *
     * @param token the token
     * @param lobbyPort the lobby port
     */
    public LobbyLoginEvent(String token, int lobbyPort) {
        this.token = token;
        this.lobbyPort = lobbyPort;
    }

    /**
     * Gets the token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the lobby port.
     *
     * @return the lobby port
     */
    public int getLobbyPort() {
        return lobbyPort;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#getType()
     */
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor)
     */
    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
