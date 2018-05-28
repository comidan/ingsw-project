package it.polimi.ingsw.sagrada.gui;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;

import java.awt.Color;

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

    private static final String defaultInitName = "Constraint";

    public static String getConstraintFileName(Constraint constraint) {

        switch(constraint) {
            case ONE : return defaultInitName+"1.png";
            case TWO : return defaultInitName+"2.png";
            case THREE : return defaultInitName+"3.png";
            case FOUR : return defaultInitName+"4.png";
            case FIVE : return defaultInitName+"5.png";
            case SIX : return defaultInitName+"6.png";
            case GREEN: return defaultInitName+"G.png";
            case YELLOW: return defaultInitName+"Y.png";
            case BLUE: return defaultInitName+"B.png";
            case PURPLE: return defaultInitName+"P.png";
            case RED : return defaultInitName+"R.png";
            case WHITE :
            default : return defaultInitName+"W.png";
        }
    }

    public static String getConstraintFileName(Constraint color, Constraint value) {
        String partialColorCompositeFileName =  getConstraintFileName(color);
        String partialValueCompositeFileName = getConstraintFileName(value);
        String valueConstraint = partialValueCompositeFileName.split("\\.")[0].substring(0, defaultInitName.length());
        String colorCotraint = partialColorCompositeFileName.split("\\.")[0].substring(0, defaultInitName.length());
        return defaultInitName+valueConstraint+colorCotraint+".png";
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

    public static Constraint getColorConstraint(Color color) {
        if(color.equals(Colors.RED))
            return Constraint.RED;
        if(color.equals(Colors.YELLOW))
            return Constraint.YELLOW;
        if(color.equals(Colors.LIGHT_BLUE))
            return Constraint.BLUE;
        if(color.equals(Colors.PURPLE))
            return Constraint.PURPLE;
        if(color.equals(Colors.GREEN))
            return Constraint.GREEN;
        else
            return Constraint.WHITE;
    }
}
