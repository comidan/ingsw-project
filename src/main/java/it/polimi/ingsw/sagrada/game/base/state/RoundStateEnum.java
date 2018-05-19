package it.polimi.ingsw.sagrada.game.base.state;

public enum RoundStateEnum {
    SETUP_ROUND,
    IN_GAME,
    END_ROUND;

    public static RoundStateEnum getFirstState() {
        return SETUP_ROUND;
    }


}