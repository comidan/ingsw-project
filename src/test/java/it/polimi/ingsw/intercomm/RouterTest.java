package it.polimi.ingsw.intercomm;

import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.Dice;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RouterTest {
    private static final Logger LOGGER = Logger.getLogger(RouterTest.class.getName());
    private static final String BASE_PATH = "src/main/resources/json/window/";
    private static final String FIRST_USER = "Mottola";
    private static final String SECOND_USER = "ingconti";
    private static final String DRAFT = "draft";

    private WindowController windowController = new WindowController();
    private DiceController diceController = new DiceController();

    @Test
    public void routerTest() {
        DiceResponse diceResponse;
        DynamicRouter dynamicRouter = new MessageDispatcher();
        List<Dice> draft = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        Player playerOne = new Player(FIRST_USER);
        Player playerTwo = new Player(SECOND_USER);
        players.add(playerOne);
        players.add(playerTwo);
        GameManager gameManager = new GameManager(players, dynamicRouter);
        dynamicRouter.subscribeChannel(WindowResponse.class, windowController);
        dynamicRouter.subscribeChannel(DiceResponse.class, diceController);
        gameManager.startGame();

        List<Message> msgs = messageGenerator("windows");
        for(Message message:msgs) {
            dynamicRouter.dispatch(message);
        }

        assertEquals(playerOne.getWindow().getName(), idWindowToName(windowController.getMessage().get(FIRST_USER).get(0), WindowSide.FRONT));
        assertEquals(playerTwo.getWindow().getName(),idWindowToName(windowController.getMessage().get(SECOND_USER).get(0), WindowSide.REAR));
        draft.addAll(diceController.getDiceResponse().getDiceList());

        msgs = messageGenerator("dice");
        for(Message message:msgs) {
            dynamicRouter.dispatch(message);
        }

        /*assertEquals(draft.get(0), playerOne.getWindow().getCellMatrix()[0][0].getCurrentDice());
        assertEquals(draft.get(1), playerTwo.getWindow().getCellMatrix()[0][0].getCurrentDice());
        assertEquals(draft.get(2), playerTwo.getWindow().getCellMatrix()[1][0].getCurrentDice());
        assertEquals(draft.get(3), playerOne.getWindow().getCellMatrix()[1][0].getCurrentDice());*/

        dynamicRouter.dispatch(new EndTurnEvent( FIRST_USER));
    }

    private List<Message> messageGenerator(String type) {
        List<Message> messages = new ArrayList<>();

        if (type.equals("windows")) {
            Map<String, List<Integer>> ids = windowController.getMessage();
            messages.add(new WindowEvent(FIRST_USER, ids.get(FIRST_USER).get(0), WindowSide.FRONT));
            messages.add(new WindowEvent(SECOND_USER, ids.get(SECOND_USER).get(0), WindowSide.REAR));
        } else if(type.equals("dice")) {
            DiceResponse diceResponse = diceController.getDiceResponse();
            messages.add(new DiceEvent(FIRST_USER, diceResponse.getDiceList().get(0).getId(), new Position(0, 0), DRAFT));
            messages.add(new EndTurnEvent(FIRST_USER));
            messages.add(new DiceEvent(SECOND_USER, diceResponse.getDiceList().get(1).getId(), new Position(0, 0), DRAFT));
            messages.add(new EndTurnEvent(SECOND_USER));
            messages.add(new DiceEvent(SECOND_USER, diceResponse.getDiceList().get(2).getId(), new Position(1, 0), DRAFT));
            messages.add(new EndTurnEvent(SECOND_USER));
            messages.add(new DiceEvent(FIRST_USER, diceResponse.getDiceList().get(3).getId(), new Position(1, 0), DRAFT));
        }

        return messages;
    }

    private String idWindowToName(int idWindow, WindowSide windowSide) {
        JSONParser jsonParser = new JSONParser();
        JSONArray windows;

        try {
            windows = (JSONArray)jsonParser.parse(new FileReader(BASE_PATH + "Windows.json"));

            JSONObject card = (JSONObject)windows.get(idWindow);
            JSONArray wnds = (JSONArray)card.get("windows");
            JSONObject wnd = (JSONObject)wnds.get(WindowSide.sideToInt(windowSide));
            return (String)wnd.get("name");

        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong reading JSON");
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong when searching for file");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Something went wrong reading file");
        }

        return null;
    }
}
