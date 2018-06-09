package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;

public class NumPlayerEvent implements Message {
    private int idPlayer;

    public NumPlayerEvent(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdPlayer() {
        return idPlayer;
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
