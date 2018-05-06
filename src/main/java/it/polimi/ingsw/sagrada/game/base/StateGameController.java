package it.polimi.ingsw.sagrada.game.base;

public enum StateGameController {
    DEAL_PRIVATE_OBJECTIVE,
    DEAL_WINDOWS,
    DEAL_TOOL,
    DEAL_PUBLIC_OBJECTIVE,
    TURN,
    SCORE;

    public static StateGameController getFirstState() {
        return DEAL_PRIVATE_OBJECTIVE;
    }
}
