package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class DiceEvent implements Message {

    private int idPlayer;
    private int idDice;
    private Position position;
    private String src;

    public DiceEvent(int idPlayer, int idDice, Position position, String src) {
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

    public int getIdPlayer() {
        return idPlayer;
    }

    public String getSrc() {
        return src;
    }

    @Override
    public Class<? extends Message> getType() {
        return  getClass();
    }
}
