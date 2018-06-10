package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;

public class OpponentDiceMoveResponse implements Message, ResponseVisitor {

    private String idPlayer;
    private Dice dice;
    private Position position;

    public OpponentDiceMoveResponse(String idPlayer, Dice dice, Position position) {
        this.idPlayer = idPlayer;
        this.dice = dice;
        this.position = position;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public Dice getDice() {
        return dice;
    }

    public Position getPosition() {
        return position;
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
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
