package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

import java.util.List;

public class PublicObjectiveResponse implements Message {

    private List<Integer> idObjective;

    public PublicObjectiveResponse(List<Integer> idObjective) {
        this.idObjective = idObjective;
    }

    public List<Integer> getIdObjective() {
        return idObjective;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
