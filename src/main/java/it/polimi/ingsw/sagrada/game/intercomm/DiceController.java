package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;

public class DiceController implements Channel<DiceResponse, Message> {
    private DiceResponse diceResponse;

    @Override
    public void dispatch(DiceResponse message) {
        diceResponse = message;
    }

    @Override
    public void sendMessage(Message message) {
    }

    public DiceResponse getDiceResponse() {
        return diceResponse;
    }
}
