package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Class WindowController.
 */
public class WindowController implements Channel<WindowResponse, Message> {
    
    /** The windows id. */
    private Map<String, List<Integer>>windowsId = new HashMap<>();

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#dispatch(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void dispatch(WindowResponse message) {
        windowsId.put(message.getPlayerId(), message.getIds());
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public Map<String, List<Integer>> getMessage() {
        return windowsId;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(Message message) {
        throw new UnsupportedOperationException();
    }
}
