package it.polimi.ingsw.sagrada.game.base;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Colors {

    public static final Color YELLOW = new Color(233, 209, 35);
    public static final Color GREEN = new Color(48, 152, 91);
    public static final Color LIGHT_BLUE = new Color(81, 169, 168);
    public static final Color PURPLE = new Color(159, 59, 124);
    public static final Color RED = new Color(198, 47, 39);

    /**
     * @return list representation of allowed colors
     */
    public static List<Color> getColorList() {
        List<Color> colorList = new ArrayList<>();
        colorList.add(YELLOW);
        colorList.add(GREEN);
        colorList.add(LIGHT_BLUE);
        colorList.add(PURPLE);
        colorList.add(RED);
        return colorList;
    }

    /**
     * @param color color to be checked
     * @return true if color is an allowed one
     */
    public static boolean isColorAllowed(Color color) {
        return getColorList().contains(color);
    }

    /**
     * @param color color to be string represented
     * @return string representation of allowed colors
     */
    public static Color stringToColor(String color) {
        switch(color) {
            case "RED": return RED;
            case "YELLOW": return YELLOW;
            case "GREEN": return GREEN;
            case "LIGHT_BLUE": return LIGHT_BLUE;
            case "PURPLE": return PURPLE;
            default: throw new ColorNotFoundException("Color not found");
        }
    }
}
