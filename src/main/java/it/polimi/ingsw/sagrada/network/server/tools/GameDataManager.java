package it.polimi.ingsw.sagrada.network.server.tools;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.EventTypeEnum;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.*;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.ClientBase;

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameDataManager implements Channel<Message, Message> {

    private static final Logger LOGGER = Logger.getLogger(GameDataManager.class.getName());

    private DynamicRouter dynamicRouter;
    private Map<String, ClientBase> clientMap;

    public GameDataManager(DynamicRouter dynamicRouter, Map<String, ClientBase> clientMap) {
        this.dynamicRouter = dynamicRouter;
        this.clientMap = clientMap;

        dynamicRouter.subscribeChannel(DiceResponse.class, this);
        dynamicRouter.subscribeChannel(WindowResponse.class, this);
        dynamicRouter.subscribeChannel(BeginTurnEvent.class, this);
        dynamicRouter.subscribeChannel(PrivateObjectiveResponse.class, this);
        dynamicRouter.subscribeChannel(PublicObjectiveResponse.class, this);
        dynamicRouter.subscribeChannel(OpponentWindowResponse.class, this);
        //dynamicRouter.subscribeChannel(RuleResponse.class, this);
    }

    @Override
    public void dispatch(Message message) {
        String msgType = message.getType().getName();

        if(msgType.equals(EventTypeEnum.toString(EventTypeEnum.DICE_RESPONSE))) {
            Iterator itr = clientMap.entrySet().iterator();
            while(itr.hasNext()) {
                Map.Entry pair = (Map.Entry)itr.next();
                try {
                    ((Client)pair.getValue()).sendResponse(message);
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }
            }
        }
        else if(msgType.equals(EventTypeEnum.toString(EventTypeEnum.WINDOW_RESPONSE))) {
            WindowResponse windowResponse = (WindowResponse)message;
            try {
                getClient(windowResponse.getPlayerId()).sendResponse(message);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
        }
        else if(msgType.equals(EventTypeEnum.toString(EventTypeEnum.BEGIN_TURN_EVENT))) {
            BeginTurnEvent beginTurnEvent = (BeginTurnEvent)message;
            try {
                getClient(beginTurnEvent.getIdPlayer()).sendResponse(message);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
        }
        else if(msgType.equals(EventTypeEnum.toString(EventTypeEnum.PRIVATE_OBJECTIVE_RESPONSE))) {
            PrivateObjectiveResponse privateObjectiveResponse = (PrivateObjectiveResponse)message;
            try {
                getClient(privateObjectiveResponse.getIdPlayer()).sendResponse(message);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
        }
        else if(msgType.equals(EventTypeEnum.toString(EventTypeEnum.PUBLIC_OBJECTIVE_RESPONSE))) {
            PublicObjectiveResponse publicObjectiveResponse = (PublicObjectiveResponse)message;
            Iterator itr = clientMap.entrySet().iterator();
            while(itr.hasNext()) {
                Map.Entry pair = (Map.Entry)itr.next();
                try {
                    ((Client)pair.getValue()).sendResponse(message);
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }
            }
        }
        else if(message instanceof OpponentWindowResponse) {
            OpponentWindowResponse opponentWindowResponse = (OpponentWindowResponse) message;
            Iterator itr = clientMap.entrySet().iterator();
            while(itr.hasNext()) {
                Map.Entry pair = (Map.Entry)itr.next();
                try {
                    ((Client)pair.getValue()).sendResponse(opponentWindowResponse);
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e::getMessage);
                }
            }
        }
        /*else if(msgType.equals(EventTypeEnum.toString(EventTypeEnum.RULE_RESPONSE))) {
            RuleResponse ruleResponse = (RuleResponse)message;
            try {
                getClient(ruleResponse.getPlayerId()).sendResponse(message);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
        }*/
    }

    @Override
    public void sendMessage(Message message) {
        dynamicRouter.dispatch(message);
    }

    private Client getClient(String id) {
        return clientMap.get(id);
    }
}
