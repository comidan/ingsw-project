package it.polimi.ingsw.sagrada.network.server.tools;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.ScoreResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.network.client.Client;
import it.polimi.ingsw.sagrada.network.client.ClientBase;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class GameDataManager.
 */
public class GameDataManager implements Channel<Message, Message>, MessageVisitor {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GameDataManager.class.getName());

    /** The dynamic router. */
    private DynamicRouter dynamicRouter;
    
    /** The client map. */
    private Map<String, ClientBase> clientMap;

    /**
     * Instantiates a new game data manager.
     *
     * @param dynamicRouter the dynamic router
     * @param clientMap the client map
     */
    public GameDataManager(DynamicRouter dynamicRouter, Map<String, ClientBase> clientMap) {
        this.dynamicRouter = dynamicRouter;
        this.clientMap = clientMap;

        dynamicRouter.subscribeChannel(DiceResponse.class, this);
        dynamicRouter.subscribeChannel(WindowResponse.class, this);
        dynamicRouter.subscribeChannel(BeginTurnEvent.class, this);
        dynamicRouter.subscribeChannel(PrivateObjectiveResponse.class, this);
        dynamicRouter.subscribeChannel(PublicObjectiveResponse.class, this);
        dynamicRouter.subscribeChannel(OpponentWindowResponse.class, this);
        dynamicRouter.subscribeChannel(OpponentDiceMoveResponse.class, this);
        dynamicRouter.subscribeChannel(RuleResponse.class, this);
        dynamicRouter.subscribeChannel(NewTurnResponse.class, this);
        dynamicRouter.subscribeChannel(ToolCardResponse.class, this);
        dynamicRouter.subscribeChannel(ScoreResponse.class, this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#dispatch(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void dispatch(Message message) {
        System.out.println("I received : " + message);
        message.accept(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(Message message) {
        dynamicRouter.dispatch(message);
    }

    /**
     * Gets the client.
     *
     * @param id the id
     * @return the client
     */
    private Client getClient(String id) {
        return clientMap.get(id);
    }

    /**
     * Send remote message.
     *
     * @param message the message
     * @param filter the filter
     */
    private void sendRemoteMessage(Message message, Function<Client, Boolean> filter) {
        Iterator itr = clientMap.entrySet().iterator();
        while(itr.hasNext()) {
            Map.Entry pair = (Map.Entry)itr.next();
            try {
                if(filter.apply((Client)pair.getValue())) {
                    System.out.println("Sending to " + ((Client) pair.getValue()).getId());
                    ((Client) pair.getValue()).sendResponse(message);
                }
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e::getMessage);
            }
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void visit(Message message) {
        LOGGER.log(Level.INFO, message.getType().getName());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.player.AddPlayerEvent)
     */
    @Override
    public void visit(AddPlayerEvent message) {
        LOGGER.log(Level.INFO, message.getType().getName());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.BeginTurnEvent)
     */
    @Override
    public void visit(BeginTurnEvent beginTurnEvent) {
        try {
            getClient(beginTurnEvent.getIdPlayer()).sendResponse(beginTurnEvent);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e::getMessage);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.lobby.MatchTimeEvent)
     */
    @Override
    public void visit(MatchTimeEvent matchTimeEvent) {
        LOGGER.log(Level.INFO, matchTimeEvent.getType().getName());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.util.HeartbeatInitEvent)
     */
    @Override
    public void visit(HeartbeatInitEvent heartbeatInitEvent) {
        LOGGER.log(Level.INFO, heartbeatInitEvent.getType().getName());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.player.RemovePlayerEvent)
     */
    @Override
    public void visit(RemovePlayerEvent removePlayerEvent) {
        LOGGER.log(Level.INFO, removePlayerEvent.getType().getName());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.window.WindowResponse)
     */
    @Override
    public void visit(WindowResponse windowResponse) {
        try {
            getClient(windowResponse.getPlayerId()).sendResponse(windowResponse);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e::getMessage);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.window.OpponentWindowResponse)
     */
    @Override
    public void visit(OpponentWindowResponse opponentWindowResponse) {
        sendRemoteMessage(opponentWindowResponse, filter -> anyone());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse)
     */
    @Override
    public void visit(DiceResponse diceResponse) {
        System.out.println("Sending " + diceResponse.getDst());
        sendRemoteMessage(diceResponse, filter -> anyone());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.OpponentDiceMoveResponse)
     */
    @Override
    public void visit(OpponentDiceMoveResponse opponentDiceMoveResponse) {
        sendRemoteMessage(opponentDiceMoveResponse, filter -> !filter.equals(getClient(opponentDiceMoveResponse.getIdPlayer())));
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.RuleResponse)
     */
    @Override
    public void visit(RuleResponse ruleResponse) {
        try {
            getClient(ruleResponse.getPlayerId()).sendResponse(ruleResponse);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e::getMessage);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.game.NewTurnResponse)
     */
    @Override
    public void visit(NewTurnResponse newTurnResponse) {
        sendRemoteMessage(newTurnResponse, filter -> anyone());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.PrivateObjectiveResponse)
     */
    @Override
    public void visit(PrivateObjectiveResponse privateObjectiveResponse) {
        try {
            getClient(privateObjectiveResponse.getIdPlayer()).sendResponse(privateObjectiveResponse);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e::getMessage);
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.PublicObjectiveResponse)
     */
    @Override
    public void visit(PublicObjectiveResponse publicObjectiveResponse) {
        sendRemoteMessage(publicObjectiveResponse, filter -> anyone());
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.card.ToolCardResponse)
     */
    @Override
    public void visit(ToolCardResponse toolCardResponse) {
        sendRemoteMessage(toolCardResponse, filter -> anyone());
    }

    /**
     * Visit.
     *
     * @param scoreResponse the score response
     */
    @Override
    public void visit(ScoreResponse scoreResponse) {
        sendRemoteMessage(scoreResponse, filter -> anyone());
    }

    /**
     * Anyone.
     *
     * @return true, if successful
     */
    private boolean anyone() {
        return true;
    }
}
