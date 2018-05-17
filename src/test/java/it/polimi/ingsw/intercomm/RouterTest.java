package it.polimi.ingsw.intercomm;

import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.Position;
import it.polimi.ingsw.sagrada.game.intercomm.*;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RouterTest {
    @Test
    public void routerTest() {
        DynamicRouter dynamicRouter = new MessageDispatcher();

        List<Player> players = new ArrayList<>();
        players.add(new Player(0));
        players.add(new Player(1));
        GameManager gameManager = new GameManager(players, dynamicRouter);
        gameManager.startGame();

        for(Message message:messageGenerator()) {
            dynamicRouter.dispatch(message);
        }
    }

    private List<Message> messageGenerator() {
        List<Message> messages = new ArrayList<>();

        messages.add(new WindowEvent(0,3, WindowSide.FRONT));
        messages.add(new WindowEvent(1,5, WindowSide.REAR));
        messages.add(new DiceEvent(0, 13, new Position(0, 0)));
        messages.add(new DiceEvent(1, 54, new Position(1, 0)));

        return messages;
    }
}
