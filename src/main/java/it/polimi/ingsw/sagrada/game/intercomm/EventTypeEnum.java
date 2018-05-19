package it.polimi.ingsw.sagrada.game.intercomm;


import java.util.logging.Level;
import java.util.logging.Logger;

public enum EventTypeEnum {
    WINDOW_GAME_MANAGER_EVENT,
    DICE_GAME_MANAGER_EVENT,
    END_TURN_EVENT;

    public static String toString(EventTypeEnum eventTypeEnum) {
        Logger logger = Logger.getLogger(EventTypeEnum.class.getName());
        switch(eventTypeEnum) {
            case WINDOW_GAME_MANAGER_EVENT: return "it.polimi.ingsw.sagrada.game.intercomm.message.WindowGameManagerEvent";
            case DICE_GAME_MANAGER_EVENT: return "it.polimi.ingsw.sagrada.game.intercomm.message.DiceGameManagerEvent";
            case END_TURN_EVENT: return "it.polimi.ingsw.sagrada.game.intercomm.message.EndTurnEvent";
            default: logger.log(Level.SEVERE, "Wrong type of Event");
        }
        return null;
    }
}
