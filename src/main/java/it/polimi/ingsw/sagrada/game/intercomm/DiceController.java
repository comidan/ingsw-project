package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;

public class DiceController implements Channel<DiceResponse, Message> {
    @Override
    public void dispatch(DiceResponse message) {

    }

    @Override
    public void sendMessage(Message message) {

    }
}
