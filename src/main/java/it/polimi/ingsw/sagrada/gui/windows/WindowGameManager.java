package it.polimi.ingsw.sagrada.gui.windows;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import it.polimi.ingsw.sagrada.gui.utils.ConstraintGenerator;

import java.util.ArrayList;
import java.util.List;

public class WindowGameManager {

    private List<Constraint[][]> constraints;
    private ConstraintGenerator constraintGenerator;

    public WindowGameManager() {
        constraints = new ArrayList<>();
        constraintGenerator = new ConstraintGenerator();
    }

    public void addWindow(int id, WindowSide side) {
        constraints.add(constraintGenerator.getConstraintMatrix(id, side));
    }

    public List<Constraint[][]> getWindows() {
        return constraints;
    }
}
