package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;

public class PrivateObjectiveResponse implements Message {

    private int idObjective;
    private String idPlayer;

    public PrivateObjectiveResponse(int idObjective, String idPlayer) {
        this.idObjective = idObjective;
        this.idPlayer = idPlayer;
    }

    public int getIdObjective() {
        return idObjective;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
