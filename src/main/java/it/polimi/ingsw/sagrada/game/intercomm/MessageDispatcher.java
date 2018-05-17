package it.polimi.ingsw.sagrada.game.intercomm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageDispatcher implements DynamicRouter<Message> {

    private Map<Class<? extends Message>, List<Channel>> handlers;

    private Logger logger = Logger.getLogger(MessageDispatcher.class.getName());

    public MessageDispatcher() {
        handlers = new HashMap<>();
    }

    @Override
    public void subscribeChannel(Class<? extends Message> contentType, Channel<? extends Message, ? extends Message> channel) {
        if(handlers.containsKey(contentType)) handlers.get(contentType).add(channel);
        else {
            List<Channel> channels = new ArrayList<>();
            channels.add(channel);
            handlers.put(contentType, channels);
        }
    }

    @Override
    public void dispatch(Message content) {
        List<Channel> channels = handlers.get(content.getType());
        if(channels!=null) {
            for (Channel c : channels) {
                c.dispatch(content);
            }
        }
        else {
            logger.log(Level.SEVERE, "Handler not found for ", content.getType().getName());
        }
    }
}
