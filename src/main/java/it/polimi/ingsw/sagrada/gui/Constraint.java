package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;

public enum Constraint {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    RED,
    GREEN,
    YELLOW,
    BLUE,
    PURPLE,
    WHITE;

    private static final String DEFAULT_CONSTRAINT_INIT_NAME = "Constraint";
    private static final String DEFAULT_DICE_INIT_NAME = "Dice";

    public static String getConstraintFileName(Constraint constraint) {

        switch(constraint) {
            case ONE : return DEFAULT_CONSTRAINT_INIT_NAME+"1.png";
            case TWO : return DEFAULT_CONSTRAINT_INIT_NAME+"2.png";
            case THREE : return DEFAULT_CONSTRAINT_INIT_NAME+"3.png";
            case FOUR : return DEFAULT_CONSTRAINT_INIT_NAME+"4.png";
            case FIVE : return DEFAULT_CONSTRAINT_INIT_NAME+"5.png";
            case SIX : return DEFAULT_CONSTRAINT_INIT_NAME+"6.png";
            case GREEN: return DEFAULT_CONSTRAINT_INIT_NAME+"G.png";
            case YELLOW: return DEFAULT_CONSTRAINT_INIT_NAME+"Y.png";
            case BLUE: return DEFAULT_CONSTRAINT_INIT_NAME+"B.png";
            case PURPLE: return DEFAULT_CONSTRAINT_INIT_NAME+"P.png";
            case RED : return DEFAULT_CONSTRAINT_INIT_NAME+"R.png";
            case WHITE :
            default : return DEFAULT_CONSTRAINT_INIT_NAME+"W.png";
        }
    }

    public static String getDiceFileName(Constraint color, Constraint value) {
        String partialColorCompositeFileName =  getConstraintFileName(color);
        String partialValueCompositeFileName = getConstraintFileName(value);
        String valueConstraint = partialValueCompositeFileName.split("\\.")[0].substring(DEFAULT_CONSTRAINT_INIT_NAME.length(), DEFAULT_CONSTRAINT_INIT_NAME.length() + 1);
        String colorConstraint = partialColorCompositeFileName.split("\\.")[0].substring(DEFAULT_CONSTRAINT_INIT_NAME.length(), DEFAULT_CONSTRAINT_INIT_NAME.length() + 1);
        return DEFAULT_DICE_INIT_NAME + valueConstraint + colorConstraint + ".png";
    }

    public static Constraint getValueConstraint(int value) {
        switch(value) {
            case 1 : return Constraint.ONE;
            case 2 : return Constraint.TWO;
            case 3 : return Constraint.THREE;
            case 4 : return Constraint.FOUR;
            case 5 : return Constraint.FIVE;
            case 6 : return Constraint.SIX;
            default : return Constraint.ONE;
        }
    }

    public static Constraint getColorConstraint(Colors color) {
        if(color.equals(Colors.RED))
            return Constraint.RED;
        if(color.equals(Colors.YELLOW))
            return Constraint.YELLOW;
        if(color.equals(Colors.BLUE))
            return Constraint.BLUE;
        if(color.equals(Colors.PURPLE))
            return Constraint.PURPLE;
        if(color.equals(Colors.GREEN))
            return Constraint.GREEN;
        else
            return Constraint.WHITE;
    }
}
