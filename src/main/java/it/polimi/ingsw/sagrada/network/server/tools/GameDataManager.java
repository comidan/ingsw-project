package it.polimi.ingsw.sagrada.network.server.tools;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.EventTypeEnum;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.WindowResponse;
import it.polimi.ingsw.sagrada.network.client.Client;

import java.rmi.RemoteException;
import java.util.*;

public class GameDataManager implements Channel<Message, Message> {
    private DynamicRouter dynamicRouter;
    private Map<String, Client> clientMap;

    public GameDataManager(DynamicRouter dynamicRouter, Map<String, Client> clientList) {
        this.dynamicRouter = dynamicRouter;
        this.clientMap = clientList;
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
                    e.printStackTrace();
                }
            }
        } else if(msgType.equals(EventTypeEnum.toString(EventTypeEnum.WINDOW_RESPONSE))) {
            WindowResponse windowResponse = (WindowResponse)message;
            try {
                getClient(windowResponse.getPlayerId()).sendResponse(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if(msgType.equals(EventTypeEnum.toString(EventTypeEnum.BEGIN_TURN_EVENT))) {
            BeginTurnEvent beginTurnEvent = (BeginTurnEvent)message;
            try {
                getClient(beginTurnEvent.getIdPlayer()).sendResponse(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessage(Message message) {
        dynamicRouter.dispatch(message);
    }

    private Client getClient(String id) {
        return clientMap.get(id);
    }
}
