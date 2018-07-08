package it.polimi.ingsw.sagrada.game.intercomm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * The Class MessageControllerDispatcher is an implementation of DynamicRouter
 */
public class MessageControllerDispatcher implements DynamicRouter<Message> {

    /** The handlers. Links a message type to all the Channel that have registered. */
    private Map<Class<? extends Message>, List<Channel>> handlers;

    /** The logger. */
    private Logger logger = Logger.getLogger(MessageControllerDispatcher.class.getName());

    /**
     * Instantiates a new message dispatcher.
     */
    public MessageControllerDispatcher() {
        handlers = new HashMap<>();
    }

    /**
     * Subscribe Channel for receiving a message
     *
     * @param contentType the type o the message
     * @param channel the channel that subscribes to contentType
     */
    @Override
    public void subscribeChannel(Class<? extends Message> contentType, Channel<? extends Message, ? extends Message> channel) {
        if(handlers.containsKey(contentType)) handlers.get(contentType).add(channel);
        else {
            List<Channel> channels = new ArrayList<>();
            channels.add(channel);
            handlers.put(contentType, channels);
        }
    }

    /**
     * Dispatch the message to all Channel that put a subscription on that type of message.
     *
     * @param content the message to be sent
     */
    @Override
    public void dispatch(Message content) {
        List<Channel> channels = handlers.get(content.getType());
        if(channels!=null) {
            for (Channel c : channels) {
                c.dispatch(content);
            }
        }
        else {
            logger.log(Level.SEVERE, () -> "Handler not found for " + content.getType());
        }
    }
}
