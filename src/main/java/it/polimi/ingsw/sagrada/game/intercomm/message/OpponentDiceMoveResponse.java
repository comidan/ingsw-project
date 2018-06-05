package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.playables.Dice;

public class OpponentDiceMoveResponse implements Message {

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
}
