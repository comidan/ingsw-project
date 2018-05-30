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

    private static final String defaultConstraintInitName = "Constraint";
    private static final String defaultDiceInitName = "Dice";

    public static String getConstraintFileName(Constraint constraint) {

        switch(constraint) {
            case ONE : return defaultConstraintInitName+"1.png";
            case TWO : return defaultConstraintInitName+"2.png";
            case THREE : return defaultConstraintInitName+"3.png";
            case FOUR : return defaultConstraintInitName+"4.png";
            case FIVE : return defaultConstraintInitName+"5.png";
            case SIX : return defaultConstraintInitName+"6.png";
            case GREEN: return defaultConstraintInitName+"G.png";
            case YELLOW: return defaultConstraintInitName+"Y.png";
            case BLUE: return defaultConstraintInitName+"B.png";
            case PURPLE: return defaultConstraintInitName+"P.png";
            case RED : return defaultConstraintInitName+"R.png";
            case WHITE :
            default : return defaultConstraintInitName+"W.png";
        }
    }

    public static String getDiceFileName(Constraint color, Constraint value) {
        String partialColorCompositeFileName =  getConstraintFileName(color);
        String partialValueCompositeFileName = getConstraintFileName(value);
        String valueConstraint = partialValueCompositeFileName.split("\\.")[0].substring(defaultConstraintInitName.length(), defaultConstraintInitName.length() + 1);
        String colorConstraint = partialColorCompositeFileName.split("\\.")[0].substring(defaultConstraintInitName.length(), defaultConstraintInitName.length() + 1);
        return defaultDiceInitName + valueConstraint + colorConstraint + ".png";
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
