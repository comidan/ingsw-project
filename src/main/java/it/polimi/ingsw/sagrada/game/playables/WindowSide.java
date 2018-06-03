package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.network.CommandKeyword;

public enum WindowSide {
    FRONT,
    REAR;

    public static int sideToInt(WindowSide side) {
        if(side==FRONT) return 0;
        else return 1;
    }

    public static String sideToString(WindowSide side) {
        if(side==FRONT)
            return CommandKeyword.FRONT;
        return CommandKeyword.REAR;
    }

    public static WindowSide stringToWindowSide(String side) {
        switch (side) {
            case "front":  return FRONT;
            case "rear": return REAR;
            default: return null;
        }
    }
}
