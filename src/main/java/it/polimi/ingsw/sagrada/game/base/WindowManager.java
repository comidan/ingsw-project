package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Picker;
import it.polimi.ingsw.sagrada.game.intercomm.*;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.Token;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WindowManager implements Channel<WindowEvent, WindowResponse> {

    private static final String BASE_PATH = "src/main/resources/json/window/";
    private static final int WINDOWS_PER_CARD = 2;
    private static final int NUM_OF_WINDOWS = 12;


    private Iterator<Integer> picker;
    private static final Logger logger = Logger.getAnonymousLogger();
    private JSONArray windowsArray;

    private Consumer<Message> dispatchGameManager;
    private DynamicRouter dynamicRouter;

    public WindowManager(Consumer<Message> dispatchGameManager, DynamicRouter dynamicRouter) {
        JSONParser parser;
        parser = new JSONParser();
        List<Integer> id = new ArrayList<>();
        for (int i = 0; i < NUM_OF_WINDOWS; i++) id.add(i);
        try {
            windowsArray = (JSONArray) parser.parse(new FileReader(BASE_PATH + "Windows.json"));
            picker = new Picker<>(id).pickerIterator();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Something breaks in reading JSON file");
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "JSON parser founds something wrong, check JSON file");
        }

        this.dispatchGameManager = dispatchGameManager;
        this.dynamicRouter = dynamicRouter;
        this.dynamicRouter.subscribeChannel(WindowEvent.class, this);
    }

    public boolean isWindowsLeft() {
        return picker.hasNext();
    }

    public void dealWindowId(int playerId) {
        List<Integer> windowId = new ArrayList<>();
        for (int i = 0; i < WINDOWS_PER_CARD; i++) {
            if (picker.hasNext()) windowId.add(picker.next());
        }

        sendMessage(new WindowResponse(playerId, windowId));
    }

    public Window generateWindow(int id, WindowSide side) {
        JSONObject card = (JSONObject) windowsArray.get(id);

        JSONArray windows = (JSONArray) card.get("windows");
        JSONObject specificWindow = (JSONObject) windows.get(WindowSide.sidetoInt(side));
        String name = (String) specificWindow.get("name");
        int numTokens = ((Long) specificWindow.get("token")).intValue();
        List<Token> tokens = new ArrayList<>();
        for (int j = 0; j < numTokens; j++) {
            tokens.add(new Token());
        }
        Cell[][] cells = createCellMatrix((JSONArray) specificWindow.get("cells"));

        return new Window(name, cells, tokens);
    }

    private Cell[][] createCellMatrix(JSONArray cells) {
        Cell[][] cellMatrix = new Cell[4][5];

        int index = 0;
        int numCellWithConstraint = cells.size();
        boolean constraintLeft = true;

        JSONObject singleCell;
        int nextX = -1;
        int nextY = -1;
        String constraint = "0";

        if (numCellWithConstraint != 0) {
            singleCell = (JSONObject) cells.get(index++);
            nextX = ((Long) singleCell.get("x")).intValue();
            nextY = ((Long) singleCell.get("y")).intValue();
            constraint = (String) singleCell.get("constraint");
        } else constraintLeft = false;

        for (int i = 0; i < cellMatrix.length; i++) { //row
            for (int j = 0; j < cellMatrix[0].length; j++) { //column
                if (constraintLeft && (i == nextY && j == nextX)) {
                    if (isNumeric(constraint)) { //if string is a number
                        cellMatrix[i][j] = new Cell(CellRule.builder().setNumberConstraint(Integer.parseInt(constraint)).build());
                    } else {
                        cellMatrix[i][j] = new Cell(CellRule.builder().setColorConstraint(Colors.stringToColor(constraint)).build());
                    }

                    if (index < numCellWithConstraint) {
                        singleCell = (JSONObject) cells.get(index++);
                        nextX = ((Long) singleCell.get("x")).intValue();
                        nextY = ((Long) singleCell.get("y")).intValue();
                        constraint = (String) singleCell.get("constraint");
                    } else constraintLeft = false;
                } else {
                    cellMatrix[i][j] = new Cell();
                }
            }
        }

        return cellMatrix;
    }

    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }


    @Override
    public void dispatch(WindowEvent message) {
        Window window = generateWindow(message.getIdWindow(), message.getWindowSide());
        dispatchGameManager.accept(new WindowGameManagerEvent(message.getIdPlayer(), window));
    }

    @Override
    public void sendMessage(WindowResponse message) {
        dynamicRouter.dispatch(message);
    }
}
