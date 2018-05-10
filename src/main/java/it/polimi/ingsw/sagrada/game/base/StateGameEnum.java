package it.polimi.ingsw.sagrada.game.base;

public enum StateGameEnum {
    DEAL_PRIVATE_OBJECTIVE,
    DEAL_WINDOWS,
    DEAL_TOOL,
    DEAL_PUBLIC_OBJECTIVE,
    TURN,
    SCORE;

    public static StateGameEnum getFirstState() {
        return DEAL_PRIVATE_OBJECTIVE;
    }
}
