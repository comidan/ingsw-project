package it.polimi.ingsw.sagrada.game.intercomm;


import java.util.logging.Level;
import java.util.logging.Logger;

public enum EventTypeEnum {
    WINDOW_GAME_MANAGER_EVENT,
    DICE_GAME_MANAGER_EVENT,
    BEGIN_TURN_EVENT,
    END_TURN_EVENT,
    DICE_RESPONSE,
    WINDOW_RESPONSE,
    NUM_PLAYER_CHANGE,
    PRIVATE_OBJECTIVE_RESPONSE,
    PUBLIC_OBJECTIVE_RESPONSE,
    RULE_RESPONSE;

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
