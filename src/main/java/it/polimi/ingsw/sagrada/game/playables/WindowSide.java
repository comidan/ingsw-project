package it.polimi.ingsw.sagrada.game.playables;

public enum WindowSide {
    FRONT,
    REAR;

    public static int sidetoInt(WindowSide side) {
        if(side==FRONT) return 0;
        else return 1;
    }
}
