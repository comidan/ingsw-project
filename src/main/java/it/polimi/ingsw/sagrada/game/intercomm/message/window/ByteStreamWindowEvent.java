package it.polimi.ingsw.sagrada.game.intercomm.message.window;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;

public class ByteStreamWindowEvent implements Message, ActionVisitor, BaseGameVisitor {

    private byte[] image;
    private String username;

    public ByteStreamWindowEvent(String username, byte[] image) {
        this.image = image;
        this.username = username;
    }

    public byte[] getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    /**
     * Accept.
     *
     * @param messageVisitor the message visitor
     */
    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    /**
     * Accept.
     *
     * @param actionMessageVisitor the action message visitor
     * @return the string
     */
    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }

    /**
     * Visit.
     *
     * @param baseGameMessageVisitor the visitor
     */
    @Override
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) {
        baseGameMessageVisitor.visit(this);
    }
}
