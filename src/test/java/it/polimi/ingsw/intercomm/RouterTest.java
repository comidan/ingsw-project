package it.polimi.ingsw.intercomm;

import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouterTest {
    @Test
    public void routerTest() {
        DynamicRouter dynamicRouter = new MessageDispatcher();

        List<Player> players = new ArrayList<>();
        players.add(new Player(0));
        players.add(new Player(1));
        GameManager gameManager = new GameManager(players, dynamicRouter);
        WindowController windowController = new WindowController();
        DiceController diceController = new DiceController();
        dynamicRouter.subscribeChannel(WindowResponse.class, windowController);
        dynamicRouter.subscribeChannel(DiceResponse.class, diceController);
        gameManager.startGame();

        for(Message message:messageGenerator(windowController)) {
            dynamicRouter.dispatch(message);
        }
    }

    private List<Message> messageGenerator(WindowController windowController) {
        List<Message> messages = new ArrayList<>();
        Map<Integer, List<Integer>> ids = windowController.getMessage();

        messages.add(new WindowEvent(0, ids.get(0).get(0), WindowSide.FRONT));
        messages.add(new WindowEvent(1, ids.get(1).get(0), WindowSide.REAR));
        messages.add(new DiceEvent(0, 13, new Position(0, 0)));
        messages.add(new DiceEvent(1, 54, new Position(1, 0)));

        return messages;
    }
}
