package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.ConstraintGenerator;

import java.util.ArrayList;
import java.util.List;


/**
 * The Class WindowGameManager.
 */
public class WindowGameManager {

    /** The constraints. */
    private List<Constraint[][]> constraints;
    
    /** The constraint generator. */
    private ConstraintGenerator constraintGenerator;

    private int playerWindowId;

    private WindowSide playerWindowSide;

    /**
     * Instantiates a new window game manager.
     */
    public WindowGameManager() {
        constraints = new ArrayList<>();
        constraintGenerator = new ConstraintGenerator();
    }

    /**
     * Adds the window.
     *
     * @param id the id
     * @param side the side
     */
    public void addWindow(int id, WindowSide side) {
        constraints.add(constraintGenerator.getConstraintMatrix(id, side));
    }

    /**
     * Gets the windows.
     *
     * @return the windows
     */
    public List<Constraint[][]> getWindows() {
        return constraints;
    }

    public int getToken() {
        return constraintGenerator.getToken(playerWindowId, playerWindowSide);
    }

    public void setPlayerWindow(int id, WindowSide side) {
        playerWindowId = id;
        playerWindowSide = side;
    }
}
