package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;

public class DiceDraftSelectionEvent implements Message, ActionVisitor{

    private String idPlayer;

    private int idDice;

    public DiceDraftSelectionEvent(String idPlayer, int idDice) {
        this.idPlayer = idPlayer;
        this.idDice = idDice;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public int getIdDice() {
        return idDice;
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
