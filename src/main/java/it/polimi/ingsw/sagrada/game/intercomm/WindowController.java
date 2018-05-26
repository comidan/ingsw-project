package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindowController implements Channel<WindowResponse, Message> {
    private Map<String, List<Integer>>windowsId = new HashMap<>();

    @Override
    public void dispatch(WindowResponse message) {
        windowsId.put(message.getPlayerId(), message.getIds());
    }

    public Map<String, List<Integer>> getMessage() {
        return windowsId;
    }

    @Override
    public void sendMessage(Message message) {

    }
}
