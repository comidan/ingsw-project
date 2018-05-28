package it.polimi.ingsw.sagrada.gui;

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
        return getConstraintFileName(color);
    }
}
