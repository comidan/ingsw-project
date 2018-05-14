package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.playables.Dice;

public class DiceGameControllerEvent extends DiceEvent {

    private Dice dice;

    public DiceGameControllerEvent(Dice dice, DiceEvent diceEvent) {
        super(diceEvent.getIdPlayer(), diceEvent.getIdDice(), diceEvent.getPosition());
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
