package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.awt.*;
import java.util.function.Function;



/**
 * The Class CellBuilder.
 *
 * @param <T> the generic type
 */
public class CellBuilder<T extends CellRule> implements Builder {

    /** The color constraint. */
    private Colors colorConstraint;
    
    /** The value constraint. */
    private Integer valueConstraint;
    
    /** The function. */
    private Function<Dice, Boolean> function;

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.base.Builder#build()
     */
    @Override
    public CellRule build() {
        if (function == null) {
            function = dice -> true;
            valueConstraint = 0;
        }
        if (valueConstraint != 0)
            return new CellRule(function, valueConstraint);
        return new CellRule(function, colorConstraint);
    }

    /**
     * Sets the color constraint.
     *
     * @param color - color constraint
     * @return this CellBuilder with an updated color rule
     */
    public CellBuilder<T> setColorConstraint(final Colors color) {
        function = dice -> color.equals(dice.getColor());
        colorConstraint = color;
        valueConstraint = 0;
        return this;
    }

    /**
     * Sets the number constraint.
     *
     * @param value - number value constraint
     * @return this CellBuilder with an updated number value rule
     */
    public CellBuilder<T> setNumberConstraint(int value) {
        function = dice -> value == dice.getValue();
        valueConstraint = value;
        colorConstraint = null;
        return this;
    }

}