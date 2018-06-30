package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.*;

public class DiceDraftSelectionEvent implements Message, ActionVisitor, ToolGameVisitor {

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

    @Override
    public void accept(ToolGameMessageVisitor toolGameMessageVisitor) { toolGameMessageVisitor.visit(this); }
}
