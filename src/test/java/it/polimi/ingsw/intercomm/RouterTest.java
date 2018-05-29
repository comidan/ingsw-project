package it.polimi.ingsw.intercomm;

import it.polimi.ingsw.sagrada.game.base.GameManager;
import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.WindowManager;
import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.game.playables.Window;
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouterTest {
    private static final Logger LOGGER = Logger.getLogger(RouterTest.class.getName());
    private static final String BASE_PATH = "src/main/resources/json/window/";
    private WindowController windowController = new WindowController();
    private DiceController diceController = new DiceController();

    public void routerTest() {
        DiceResponse diceResponse;
        DynamicRouter dynamicRouter = new MessageDispatcher();

        List<Player> players = new ArrayList<>();
        Player playerOne = new Player("Mottola");
        Player playerTwo = new Player("Ingconti");
        players.add(playerOne);
        players.add(playerTwo);
        GameManager gameManager = new GameManager(players, dynamicRouter);
        WindowManager windowManager = new WindowManager(gameManager.getDispatchReference(), dynamicRouter);
        Window window = windowManager.generateWindow(0, WindowSide.FRONT);
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 5; j++)
                System.out.println("Must be FALSE : " + window.getCellMatrix()[i][j].isOccupied());
        playerOne.setWindow(window);
        window = windowManager.generateWindow(0, WindowSide.REAR);
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 5; j++)
                System.out.println("Must be FALSE : " + window.getCellMatrix()[i][j].isOccupied());
        playerTwo.setWindow(window);
        dynamicRouter.subscribeChannel(WindowResponse.class, windowController);
        dynamicRouter.subscribeChannel(DiceResponse.class, diceController);
        gameManager.startGame();

        for(Message message:messageGenerator("window")) {
            dynamicRouter.dispatch(message);
        }

        assertEquals
                (idWindowToName(windowController.getMessage().get("Mottola").get(0), WindowSide.FRONT),
                        playerOne.getWindow().getName());
        assertEquals
                (idWindowToName(windowController.getMessage().get("Ingconti").get(0), WindowSide.REAR),
                        playerTwo.getWindow().getName());

        for(Message message:messageGenerator("dice")) {
            dynamicRouter.dispatch(message);
        }
        diceResponse = diceController.getDiceResponse();

        assertEquals(
                diceResponse.getDiceList().get(0),
                playerOne.getWindow().getCellMatrix()[0][0].getCurrentDice());
        assertEquals(
                diceResponse.getDiceList().get(1),
                playerTwo.getWindow().getCellMatrix()[0][0].getCurrentDice());
        assertEquals(
                diceResponse.getDiceList().get(2),
                playerTwo.getWindow().getCellMatrix()[1][0].getCurrentDice());
        assertEquals(
                diceResponse.getDiceList().get(3),
                playerOne.getWindow().getCellMatrix()[1][0].getCurrentDice());

        dynamicRouter.dispatch(new EndTurnEvent("Mottola"));
    }

    private List<Message> messageGenerator(String type) {
        List<Message> messages = new ArrayList<>();

        if(type.equals("window")){
            Map<String, List<Integer>> ids = windowController.getMessage();
            messages.add(new WindowEvent("Mottola", ids.get("Mottola").get(0), WindowSide.FRONT));
            messages.add(new WindowEvent("Ingconti", ids.get("Ingconti").get(0), WindowSide.REAR));
        } else if(type.equals("dice")) {
            DiceResponse diceResponse = diceController.getDiceResponse();
            messages.add(new DiceEvent("Mottola", diceResponse.getDiceList().get(0).getId(), new Position(0, 0), "draft"));
            messages.add(new EndTurnEvent("Mottola"));
            messages.add(new DiceEvent("Ingconti", diceResponse.getDiceList().get(1).getId(), new Position(0, 0), "draft"));
            messages.add(new EndTurnEvent("Ingconti"));
            messages.add(new DiceEvent("Ingconti", diceResponse.getDiceList().get(2).getId(), new Position(1, 0), "draft"));
            messages.add(new EndTurnEvent("Ingconti"));
            messages.add(new DiceEvent("Mottola", diceResponse.getDiceList().get(3).getId(), new Position(1, 0), "draft"));
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
            JSONObject wnd = (JSONObject)wnds.get(WindowSide.sidetoInt(windowSide));
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
