package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class DiceEvent implements Message {

    private int idPlayer;
    private int idDice;
    private Position position;

    public DiceEvent(int idPlayer, int idDice, Position position) {
        this.idPlayer = idPlayer;
        this.idDice = idDice;
        this.position = position;
    }

    public int getIdDice() {
        return idDice;
    }

    public Position getPosition() {
        return position;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    @Override
    public Class<? extends Message> getType() {
        return  getClass();
    }
}
