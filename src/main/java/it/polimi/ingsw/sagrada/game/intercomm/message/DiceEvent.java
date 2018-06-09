package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.ActionMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;

public class DiceEvent implements Message, ActionVisitor {

    private String idPlayer;
    private int idDice;
    private Position position;
    private String src;

    public DiceEvent(String idPlayer, int idDice, Position position, String src) {
        this.idPlayer = idPlayer;
        this.idDice = idDice;
        this.position = position;
        this.src = src;
    }

    public int getIdDice() {
        return idDice;
    }

    public Position getPosition() {
        return position;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public String getSrc() {
        return src;
    }

    @Override
    public Class<? extends Message> getType() {
        return  getClass();
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
