package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.network.CommandKeyword;



/**
 * The Enum WindowSide.
 */
public enum WindowSide {
    
    /** The front. */
    FRONT,
    
    /** The rear. */
    REAR;

    /**
     * Side to int.
     *
     * @param side the side
     * @return the int
     */
    public static int sideToInt(WindowSide side) {
        if(side==FRONT) return 0;
        else return 1;
    }

    /**
     * Side to string.
     *
     * @param side the side
     * @return the string
     */
    public static String sideToString(WindowSide side) {
        if(side==FRONT)
            return CommandKeyword.FRONT;
        return CommandKeyword.REAR;
    }

    /**
     * String to window side.
     *
     * @param side the side
     * @return the window side
     */
    public static WindowSide stringToWindowSide(String side) {
        switch (side) {
            case "front":  return FRONT;
            case "rear": return REAR;
            default: return null;
        }
    }
}
