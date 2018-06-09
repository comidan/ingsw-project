package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.ActionMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;

public class EndTurnEvent implements Message, ActionVisitor {
    private String idPlayer;

    public EndTurnEvent(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getIdPlayer() {
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

    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }
}
