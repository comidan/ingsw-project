package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.Picker;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowGameManagerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.playables.Token;
import it.polimi.ingsw.sagrada.game.playables.Window;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.game.rules.CellRule;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;


/**
 * Window manager class, bridge from json window representation to the dynamic model one
 */
public class WindowManager implements Channel<WindowEvent, WindowResponse> {

    private static final String BASE_PATH = "/json/window/Windows.json";

    private static final int WINDOWS_PER_CARD = 2;

    private static final int NUM_OF_WINDOWS = 12;

    private Iterator<Integer> picker;

    private static final Logger logger = Logger.getAnonymousLogger();

    private JSONArray windowsArray;

    private Consumer<Message> dispatchGameManager;

    private DynamicRouter dynamicRouter;

    /**
     * Instantiates a new window manager.
     *
     * @param dispatchGameManager the dispatch game manager
     * @param dynamicRouter the dynamic router
     */
    public WindowManager(Consumer<Message> dispatchGameManager, DynamicRouter dynamicRouter) {
        JSONParser parser;
        parser = new JSONParser();
        List<Integer> id = new ArrayList<>();
        IntStream.range(0, NUM_OF_WINDOWS).forEach(id::add);
        try {
            windowsArray = (JSONArray) parser.parse(new InputStreamReader(WindowManager.class.getResourceAsStream(BASE_PATH)));
            picker = new Picker<>(id).pickerIterator();
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Something breaks in reading JSON file");
        }
        catch (ParseException e) {
            logger.log(Level.SEVERE, "JSON parser founds something wrong, check JSON file");
        }

        this.dispatchGameManager = dispatchGameManager;
        this.dynamicRouter = dynamicRouter;
        this.dynamicRouter.subscribeChannel(WindowEvent.class, this);
    }

    /**
     * Deal window id.
     *
     * @param playerId the player id
     */
    public void dealWindowId(String playerId) {
        List<Integer> windowId = new ArrayList<>();
        IntStream.range(0, WINDOWS_PER_CARD).forEach(i -> {
            if(picker.hasNext())
                windowId.add(picker.next());
        });
        sendMessage(new WindowResponse(playerId, windowId));
    }

    /**
     * Generate window.
     *
     * @param id the id
     * @param side the side
     * @return the window
     */
    public Window generateWindow(int id, WindowSide side) {
        JSONObject card = (JSONObject) windowsArray.get(id);
        JSONArray windows = (JSONArray) card.get("windows");
        JSONObject specificWindow = (JSONObject) windows.get(WindowSide.sideToInt(side));
        String name = (String) specificWindow.get("name");
        int numTokens = ((Long) specificWindow.get("token")).intValue();
        List<Token> tokens = new ArrayList<>();
        IntStream.range(0, numTokens).forEach(i -> tokens.add(new Token()));
        Cell[][] cells = createCellMatrix((JSONArray) specificWindow.get("cells"));
        return new Window(name, cells, tokens, id, side);
    }

    /**
     * Creates the cell matrix.
     *
     * @param cells the cells
     * @return the cell[][]
     */
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

    /**
     * Checks if is numeric.
     *
     * @param s the s
     * @return true, if is numeric
     */
    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }


    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#dispatch(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void dispatch(WindowEvent message) {
        Window window = generateWindow(message.getIdWindow(), message.getWindowSide());
        dispatchGameManager.accept(new WindowGameManagerEvent(message.getIdPlayer(), window));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(WindowResponse message) {
        dynamicRouter.dispatch(message);
    }
}
