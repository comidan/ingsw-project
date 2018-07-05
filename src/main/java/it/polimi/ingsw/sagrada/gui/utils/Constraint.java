package it.polimi.ingsw.sagrada.gui.utils;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;


/**
 * The Enum Constraint.
 */
public enum Constraint {
    
    /** The one. */
    ONE,
    
    /** The two. */
    TWO,
    
    /** The three. */
    THREE,
    
    /** The four. */
    FOUR,
    
    /** The five. */
    FIVE,
    
    /** The six. */
    SIX,
    
    /** The red. */
    RED,
    
    /** The green. */
    GREEN,
    
    /** The yellow. */
    YELLOW,
    
    /** The blue. */
    BLUE,
    
    /** The purple. */
    PURPLE,
    
    /** The white. */
    WHITE;

    /** The Constant DEFAULT_CONSTRAINT_INIT_NAME. */
    private static final String DEFAULT_CONSTRAINT_INIT_NAME = "Constraint";
    
    /** The Constant DEFAULT_DICE_INIT_NAME. */
    private static final String DEFAULT_DICE_INIT_NAME = "Dice";

    /**
     * Gets the constraint file name.
     *
     * @param constraint the constraint
     * @return the constraint file name
     */
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

    /**
     * Gets the dice file name.
     *
     * @param color the color
     * @param value the value
     * @return the dice file name
     */
    public static String getDiceFileName(Constraint color, Constraint value) {
        String partialColorCompositeFileName =  getConstraintFileName(color);
        String partialValueCompositeFileName = getConstraintFileName(value);
        String valueConstraint = partialValueCompositeFileName.split("\\.")[0].substring(DEFAULT_CONSTRAINT_INIT_NAME.length(), DEFAULT_CONSTRAINT_INIT_NAME.length() + 1);
        String colorConstraint = partialColorCompositeFileName.split("\\.")[0].substring(DEFAULT_CONSTRAINT_INIT_NAME.length(), DEFAULT_CONSTRAINT_INIT_NAME.length() + 1);
        return DEFAULT_DICE_INIT_NAME + valueConstraint + colorConstraint + ".png";
    }

    /**
     * Gets the value constraint.
     *
     * @param value the value
     * @return the value constraint
     */
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

    /**
     * Gets the color constraint.
     *
     * @param color the color
     * @return the color constraint
     */
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

    public static Colors getColorFromConstraint(Constraint constraint) {
        switch (constraint) {
            case GREEN:
                return Colors.GREEN;
            case YELLOW:
                return Colors.YELLOW;
            case BLUE:
                return Colors.BLUE;
            case PURPLE:
                return Colors.PURPLE;
            case RED:
                return Colors.RED;
            default:
                return Colors.BLACK;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        switch (this) {
            case ONE:
                return "1";
            case TWO:
                return "2";
            case THREE:
                return "3";
            case FOUR:
                return "4";
            case FIVE:
                return "5";
            case SIX:
                return "6";
            case GREEN:
                return "G";
            case YELLOW:
                return "Y";
            case BLUE:
                return "B";
            case PURPLE:
                return "P";
            case RED:
                return "R";
            case WHITE:
                return "W";
            default:
                return "NOPE";
        }
    }
}
