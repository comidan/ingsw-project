package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;


/**
 * The Class DiceController.
 */
public class DiceController implements Channel<DiceResponse, Message> {
    
    /** The dice response. */
    private DiceResponse diceResponse;

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#dispatch(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void dispatch(DiceResponse message) {
        diceResponse = message;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(Message message) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the dice response.
     *
     * @return the dice response
     */
    public DiceResponse getDiceResponse() {
        return diceResponse;
    }
}
