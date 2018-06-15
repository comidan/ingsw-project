package it.polimi.ingsw.sagrada.game.intercomm;


import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Enum EventTypeEnum.
 */
public enum EventTypeEnum {
    
    /** The window game manager event. */
    WINDOW_GAME_MANAGER_EVENT,
    
    /** The dice game manager event. */
    DICE_GAME_MANAGER_EVENT,
    
    /** The begin turn event. */
    BEGIN_TURN_EVENT,
    
    /** The end turn event. */
    END_TURN_EVENT,
    
    /** The dice response. */
    DICE_RESPONSE,
    
    /** The window response. */
    WINDOW_RESPONSE,
    
    /** The num player change. */
    NUM_PLAYER_CHANGE,
    
    /** The private objective response. */
    PRIVATE_OBJECTIVE_RESPONSE,
    
    /** The public objective response. */
    PUBLIC_OBJECTIVE_RESPONSE,
    
    /** The rule response. */
    RULE_RESPONSE;

    /**
     * To string.
     *
     * @param eventTypeEnum the event type enum
     * @return the string
     */
    public static String toString(EventTypeEnum eventTypeEnum) {
        Logger logger = Logger.getLogger(EventTypeEnum.class.getName());
        switch(eventTypeEnum) {
            case WINDOW_GAME_MANAGER_EVENT:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowGameManagerEvent";
            case DICE_GAME_MANAGER_EVENT:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceGameManagerEvent";
            case BEGIN_TURN_EVENT:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent";
            case END_TURN_EVENT:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent";
            case NUM_PLAYER_CHANGE:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.game.NumPlayerEvent";
            case DICE_RESPONSE:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse";
            case WINDOW_RESPONSE:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse";
            case PRIVATE_OBJECTIVE_RESPONSE:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse";
            case PUBLIC_OBJECTIVE_RESPONSE:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse";
            case RULE_RESPONSE:
                return "it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse";
            default: logger.log(Level.SEVERE, "Wrong type of Event");
        }
        return null;
    }
}
