package it.polimi.ingsw.sagrada.gui.utils;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class ConstraintGenerator.
 */
public class ConstraintGenerator {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    
    /** The Constant BASE_PATH. */
    private static final String BASE_PATH = "/json/window/";
    
    /** The windows array. */
    private JSONArray windowsArray;

    /**
     * Instantiates a new constraint generator.
     */
    public ConstraintGenerator() {
        try {
            JSONParser parser = new JSONParser();
            windowsArray = (JSONArray) parser.parse(new InputStreamReader(ConstraintGenerator.class
                                                    .getResourceAsStream(BASE_PATH + "Windows.json")));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Something breaks in reading JSON file");
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "JSON parser founds something wrong, check JSON file");
        }
    }

    /**
     * Gets the constraint matrix.
     *
     * @param id the id
     * @param side the side
     * @return the constraint matrix
     */
    public Constraint[][] getConstraintMatrix(int id, WindowSide side) {
        JSONObject card = (JSONObject) windowsArray.get(id);
        JSONArray windows = (JSONArray) card.get("windows");
        JSONObject specificWindow = (JSONObject) windows.get(WindowSide.sideToInt(side));
        return getMatrix((JSONArray)specificWindow.get("cells"));
    }
    
    /**
     * Gets the cell matrix for the window.
     *
     * @param cells the cells
     * @return the matrix
     */
    private Constraint[][] getMatrix(JSONArray cells) {
        Constraint[][] constraintMatrix = new Constraint[4][5];

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

        for (int i = 0; i < constraintMatrix.length; i++) { //row
            for (int j = 0; j < constraintMatrix[0].length; j++) { //column
                if (constraintLeft && (i == nextY && j == nextX)) {
                    if (isNumeric(constraint)) { //if string is a number
                        constraintMatrix[i][j] = Constraint.getValueConstraint(Integer.parseInt(constraint));
                    } else {
                        constraintMatrix[i][j] = Constraint.getColorConstraint(Colors.stringToColor(constraint));
                    }
                    if (index < numCellWithConstraint) {
                        singleCell = (JSONObject) cells.get(index++);
                        nextX = ((Long) singleCell.get("x")).intValue();
                        nextY = ((Long) singleCell.get("y")).intValue();
                        constraint = (String) singleCell.get("constraint");
                    } else constraintLeft = false;
                } else {
                    constraintMatrix[i][j] = Constraint.WHITE;
                }
            }
        }
        return constraintMatrix;
    }

    /**
     * Checks if input is numeric.
     *
     * @param s the input
     * @return true, if input is numeric
     */
    private boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    /**
     * Gets the token associated to the window.
     *
     * @param id the id
     * @param side the side
     * @return the token
     */
    public int getToken(int id, WindowSide side) {
        JSONObject card = (JSONObject) windowsArray.get(id);
        JSONArray windows = (JSONArray) card.get("windows");
        JSONObject specificWindow = (JSONObject) windows.get(WindowSide.sideToInt(side));
        int token = ((Long)specificWindow.get("token")).intValue();
        return token;
    }
}
