package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;
import it.polimi.ingsw.sagrada.game.base.Colors;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.awt.*;
import java.util.function.Function;

/**
 *
 */
public class CellBuilder<T extends CellRule> implements Builder {

    private Color colorConstraint;
    private Integer valueConstraint;
    private Function<Dice, Boolean> function;

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
     * @param color - color constraint
     * @return this CellBuilder with an updated color rule
     */
    public CellBuilder<T> setColorConstraint(final Color color) {
        function = dice -> color.equals(dice.getColor());
        colorConstraint = color;
        valueConstraint = 0;
        return this;
    }

    /**
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