package it.polimi.ingsw.sagrada.game.base.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Colors enum making possible to distinguish between various colors
 */
public enum Colors {

    YELLOW,

    RED,

    GREEN,

    BLUE,

    PURPLE,

    /** Error color for some computational work */
    BLACK;

    private static final Logger LOGGER = Logger.getLogger(Colors.class.getName());

    /**
     * Gets allowed colors as list
     *
     * @return the color list
     */
    public static List<Colors> getColorList() {
        List<Colors> colorList = new ArrayList<>();
        colorList.add(YELLOW);
        colorList.add(GREEN);
        colorList.add(BLUE);
        colorList.add(PURPLE);
        colorList.add(RED);
        return colorList;
    }

    /**
     * Cast color string equivalent to color
     *
     * @param color the color
     * @return the colors
     */
    public static Colors stringToColor(String color) {
        switch(color) {
            case "RED": return RED;
            case "YELLOW": return YELLOW;
            case "GREEN": return GREEN;
            case "BLUE": return BLUE;
            case "PURPLE": return PURPLE;
            default: LOGGER.log(Level.SEVERE, () -> "Not a valid color!!! Ing Conti is judging you");
                return null;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        switch(this) {
            case RED: return "RED";
            case YELLOW: return "YELLOW";
            case GREEN: return "GREEN";
            case BLUE: return "BLUE";
            case PURPLE: return "PURPLE";
            default: return "";
        }
    }

    /**
     * Get initial of the current color
     *
     * @return the color's initial letter
     */
    public String toStringSingleLetter() {
        switch(this) {
            case RED: return "R";
            case YELLOW: return "Y";
            case GREEN: return "G";
            case BLUE: return "B";
            case PURPLE: return "P";
            default: return "";
        }
    }

    public int toInt() {
        switch(this) {
            case RED: return 0;
            case YELLOW: return 1;
            case GREEN: return 2;
            case BLUE: return 3;
            case PURPLE: return 4;
            default: return -1;
        }
    }
}
