package it.polimi.ingsw.sagrada.game.playables;

public enum WindowSide {
    FRONT,
    REAR;

    public static int sidetoInt(WindowSide side) {
        if(side==FRONT) return 0;
        else return 1;
    }

    public static WindowSide stringtoWindowSide(String side) {
        switch (side) {
            case "front":  return FRONT;
            case "rear": return REAR;
            default: return null;
        }
    }
}
