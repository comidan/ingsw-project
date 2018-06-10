package it.polimi.ingsw.sagrada.game.intercomm.message.card;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;

public class PrivateObjectiveResponse implements Message, ResponseVisitor {

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

    @Override
    public String accept(ResponseMessageVisitor responseMessageVisitor) {
        return responseMessageVisitor.visit(this);
    }
}
