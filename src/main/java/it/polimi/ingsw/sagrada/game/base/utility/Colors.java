package it.polimi.ingsw.sagrada.game.base.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum Colors {
    YELLOW,
    RED,
    GREEN,
    BLUE,
    PURPLE,
    BLACK; //error color

    private static final Logger LOGGER = Logger.getLogger(Colors.class.getName());

    public static List<Colors> getColorList() {
        List<Colors> colorList = new ArrayList<>();
        colorList.add(YELLOW);
        colorList.add(GREEN);
        colorList.add(BLUE);
        colorList.add(PURPLE);
        colorList.add(RED);
        return colorList;
    }

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
}
