package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.List;

public class DiceResponse implements Message {

    private String dst;
    private List<Dice> diceList;

    public DiceResponse(String dst, List<Dice> diceList) {
        this.dst = dst;
        this.diceList = diceList;
    }

    public String getDst() {
        return dst;
    }

    public List<Dice> getDiceList() {
        return diceList;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
