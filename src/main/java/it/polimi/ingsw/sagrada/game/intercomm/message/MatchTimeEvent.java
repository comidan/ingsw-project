package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class MatchTimeEvent implements Message {

    private String time;

    public MatchTimeEvent(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }


    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
