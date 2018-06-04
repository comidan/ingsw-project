package it.polimi.ingsw.sagrada.game.intercomm.message;


import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class RuleResponse implements Message {

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

    public boolean isMoveValid() {
        return validMove;
    }

    public String getPlayerId() {
        return playerId;
    }
}
