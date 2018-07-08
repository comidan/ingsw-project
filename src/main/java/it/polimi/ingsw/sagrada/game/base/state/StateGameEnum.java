package it.polimi.ingsw.sagrada.game.base.state;



/**
 * StateGameEnum make possible detecting in which state the game is.
 */
public enum StateGameEnum {
    
    /**  Delivering private objectives. */
    DEAL_PRIVATE_OBJECTIVE,
    
    /**  Delivering tools. */
    DEAL_TOOL,
    
    /**  Delivering public objectives. */
    DEAL_PUBLIC_OBJECTIVE,
    
    /**  Delivering windows. */
    DEAL_WINDOWS,
    
    /**  Playing a turn. */
    TURN,
    
    /**  Ending game, computing final scores. */
    SCORE;

    /**
     * Gets the first state in which every game finds itself at first.
     *
     * @return the first game state
     */
    public static StateGameEnum getFirstState() {
        return DEAL_PRIVATE_OBJECTIVE;
    }
}
