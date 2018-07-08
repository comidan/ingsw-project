package it.polimi.ingsw.sagrada.game.intercomm.message.dice;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ResponseVisitor;
import it.polimi.ingsw.sagrada.game.playables.Dice;

import java.util.List;

public class DiceRoundTrackReconnectionEvent implements Message, ResponseVisitor {
    private List<List<Dice>> roundTrack;
    private String playerId;

    public DiceRoundTrackReconnectionEvent(List<List<Dice>> roundTrack, String playerId) {
        this.roundTrack = roundTrack;
        this.playerId = playerId;
    }

    public List<List<Dice>> getRoundTrack() {
        return roundTrack;
    }

    public String getPlayerId() {
        return playerId;
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
