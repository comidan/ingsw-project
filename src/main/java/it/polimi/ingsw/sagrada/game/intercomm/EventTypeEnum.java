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
    NUM_PLAYER_CHANGE;

    public static String toString(EventTypeEnum eventTypeEnum) {
        Logger logger = Logger.getLogger(EventTypeEnum.class.getName());
        switch(eventTypeEnum) {
            case WINDOW_GAME_MANAGER_EVENT: return "it.polimi.ingsw.sagrada.game.intercomm.message.WindowGameManagerEvent";
            case DICE_GAME_MANAGER_EVENT: return "it.polimi.ingsw.sagrada.game.intercomm.message.DiceGameManagerEvent";
            case BEGIN_TURN_EVENT: return "it.polimi.ingsw.sagrada.game.intercomm.message.BeginTurnEvent";
            case END_TURN_EVENT: return "it.polimi.ingsw.sagrada.game.intercomm.message.EndTurnEvent";
            case NUM_PLAYER_CHANGE: return "it.polimi.ingsw.sagrada.game.intercomm.message.NumPlayerEvent";
            case DICE_RESPONSE: return "it.polimi.ingsw.sagrada.game.intercomm.message.DiceResponse";
            case WINDOW_RESPONSE: return "it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse";
            default: logger.log(Level.SEVERE, "Wrong type of Event");
        }
        return null;
    }
}
