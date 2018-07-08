package it.polimi.ingsw.cards;

import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.WindowManager;
import it.polimi.ingsw.sagrada.game.cards.CardManager;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageControllerDispatcher;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToolManagerTest {
    @Test
    public void toolManagerTest() {
        DynamicRouter dynamicRouter = new MessageControllerDispatcher();
        Map<String, Player> players = new HashMap<>();
        Player player = new Player("Banana");
        players.put("banana", player);
        WindowManager windowManager = new WindowManager(null, dynamicRouter);
        Receiver receiver = new Receiver(dynamicRouter);
        CardManager cardManager = new CardManager();
        List<ToolCard> tools = cardManager.dealTool();
        ToolManager toolManager = new ToolManager(tools, players, null, null, dynamicRouter);

        Window window = windowManager.generateWindow(0, WindowSide.FRONT);
        player.setWindow(window);
        assertFalse(player.getTokens()==0);

        dynamicRouter.dispatch(new ToolEvent("banana", tools.get(0).getId()));
        assertEquals(true, receiver.getToolResponse().isCanBuy());
        dynamicRouter.dispatch(new EndTurnEvent("banana"));
        dynamicRouter.dispatch(new ToolEvent("banana", tools.get(1).getId()));
        assertEquals(true, receiver.getToolResponse().isCanBuy());
        dynamicRouter.dispatch(new EndTurnEvent("banana"));
        dynamicRouter.dispatch(new ToolEvent("banana", tools.get(2).getId()));
        assertEquals(true, receiver.getToolResponse().isCanBuy());
        dynamicRouter.dispatch(new EndTurnEvent("banana"));
    }

    @Test
    public void toolUsageTest() {

    }

    private class Receiver implements Channel<ToolResponse, Message> {

        ToolResponse toolResponse;

        Receiver(DynamicRouter dynamicRouter) {
            dynamicRouter.subscribeChannel(ToolResponse.class, this);
        }

        ToolResponse getToolResponse() {
            return toolResponse;
        }

        @Override
        public void dispatch(ToolResponse message) {
            toolResponse = message;
        }

        @Override
        public void sendMessage(Message message) {

        }
    }
}
