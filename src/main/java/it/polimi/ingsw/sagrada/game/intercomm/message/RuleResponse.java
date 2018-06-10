package it.polimi.ingsw.sagrada.game.intercomm.message;


import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ResponseVisitor;

public class RuleResponse implements Message, ResponseVisitor {

    private boolean validMove;
    private String playerId;

    public RuleResponse(String playerId, boolean validMove) {
        this.validMove = validMove;
        this.playerId = playerId;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    public boolean isMoveValid() {
        return validMove;
    }

    public String getPlayerId() {
        return playerId;
    }

    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
