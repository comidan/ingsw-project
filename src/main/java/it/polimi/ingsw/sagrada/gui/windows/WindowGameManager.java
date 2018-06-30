package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.base.utility.Pair;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.ConstraintGenerator;

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
    
    /** The constraint generator. */
    private ConstraintGenerator constraintGenerator;

    private int playerWindowId;

    private WindowSide playerWindowSide;

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

    public int getToken() {
        return constraintGenerator.getToken(playerWindowId, playerWindowSide);
    }

    public void setPlayerWindow(int id, WindowSide side) {
        playerWindowId = id;
        playerWindowSide = side;
    }

    public void setWindows(Map<String, Pair<Integer, WindowSide>> windows, List<String> players) {
        players.forEach(player -> addWindow(player, windows.get(player).getFirstEntry(), windows.get(player).getSecondEntry()));
    }
}
