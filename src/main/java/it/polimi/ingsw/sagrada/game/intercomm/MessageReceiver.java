package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.base.utility.Position;
import it.polimi.ingsw.sagrada.game.intercomm.message.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowEvent;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReceiver {
    private static final Logger LOGGER = Logger.getLogger(MessageReceiver.class.getName());
    private DynamicRouter dynamicRouter;

    public MessageReceiver(DynamicRouter dynamicRouter) {
        this.dynamicRouter = dynamicRouter;
    }

    public void receive(String message) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonMsg = (JSONObject)parser.parse(message);
            JSONObject data;
            switch ((String)jsonMsg.get("type_cmd")) {
                case "choice_window":
                    int idPlayerW = ((Long)jsonMsg.get("id_player")).intValue();
                    data = (JSONObject) jsonMsg.get("window");
                    int windowId = ((Long)data.get("window_id")).intValue();
                    WindowSide windowSide = WindowSide.stringtoWindowSide((String)data.get("window_side"));
                    dynamicRouter.dispatch(new WindowEvent(idPlayerW, windowId, windowSide));
                    break;
                case "choice_move_dice":
                    data = (JSONObject) jsonMsg.get("move_dice");
                    int idPlayer = ((Long)data.get("player_id")).intValue();
                    int idDice = ((Long)data.get("dice_id")).intValue();
                    String source = (String)data.get("source");
                    JSONObject pos = (JSONObject)data.get("position");
                    int row = ((Long)pos.get("y")).intValue();
                    int col = ((Long)pos.get("x")).intValue();
                    Position position = new Position(row, col);
                    dynamicRouter.dispatch(new DiceEvent(idPlayer, idDice, position, source)); break;
                case "end_turn":
                    dynamicRouter.dispatch(new EndTurnEvent(((Long)jsonMsg.get("id_player")).intValue())); break;
                default: LOGGER.log(Level.SEVERE, "Message not recognised");
            }

        }catch (ParseException exc) {
            LOGGER.log(Level.SEVERE, "Error parsing JSON message");
        }
    }
}
