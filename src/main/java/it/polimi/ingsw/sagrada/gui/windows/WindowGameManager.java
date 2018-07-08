package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.base.utility.Pair;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.ConstraintGenerator;
import javafx.geometry.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * The Class WindowGameManager.
 */
public class WindowGameManager {

    /** The constraints. */
    private Map<String, Constraint[][]> constraints;

    /** The window info. */
    private Map<String, Pair<Integer, WindowSide>> windowInfo;
    
    /** The constraint generator. */
    private ConstraintGenerator constraintGenerator;

    /**
     * Instantiates a new window game manager.
     */
    public WindowGameManager() {
        constraints = new HashMap<>();
        constraintGenerator = new ConstraintGenerator();
    }

    /**
     * Adds the window.
     *
     * @param username the username
     * @param id the id
     * @param side the side
     */
    public void addWindow(String username, int id, WindowSide side) {
        constraints.put(username, constraintGenerator.getConstraintMatrix(id, side));
    }

    /**
     * Gets the windows.
     *
     * @return the windows
     */
    public Map<String, Constraint[][]> getWindows() {
        return constraints;
    }

    /**
     * Gets the token.
     *
     * @param username the username
     * @return the token
     */
    public int getToken(String username) {
        Pair<Integer, WindowSide> pair = windowInfo.get(username);
        int playerWindowId = pair.getFirstEntry();
        WindowSide playerWindowSide = pair.getSecondEntry();

        return constraintGenerator.getToken(playerWindowId, playerWindowSide);
    }

    /**
     * Sets the windows.
     *
     * @param windows the windows
     * @param players the players
     */
    public void setWindows(Map<String, Pair<Integer, WindowSide>> windows, List<String> players) {
        players.forEach(player -> addWindow(player, windows.get(player).getFirstEntry(), windows.get(player).getSecondEntry()));
        windowInfo = windows;
    }
}
