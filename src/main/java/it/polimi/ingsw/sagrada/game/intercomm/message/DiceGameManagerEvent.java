package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.playables.Dice;

public class DiceGameManagerEvent extends DiceEvent {

    private Dice dice;

    public DiceGameManagerEvent(Dice dice, DiceEvent diceEvent) {
        super(diceEvent.getIdPlayer(), diceEvent.getIdDice(), diceEvent.getPosition(), diceEvent.getSrc());
        this.dice = dice;
    }

    public Dice getDice() {
        return dice;
    }

    @Override
    public Class<? extends Message> getType() {
        return  getClass();
    }
}
