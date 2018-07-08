package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.DTO;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.CompleteSwapDiceToolMessage;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.RoundTrackToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackVisitor;
import it.polimi.ingsw.sagrada.network.CommandKeyword;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;



/**
 * The Class RoundTrack.
 */
public class RoundTrack implements Channel<Message, Message>, RoundTrackMessageVisitor {

    /** The Constant MAX_ROUND. */
    private static final int MAX_ROUND = 10;

    /** The round dice. */
    private List<List<Dice>> roundDice;

    /** The dynamic router. */
    private DynamicRouter dynamicRouter;

    /**
     * Instantiates a new round track.
     *
     * @param dynamicRouter the dynamic router
     */
    public RoundTrack(DynamicRouter dynamicRouter) {
        roundDice = new ArrayList<>(MAX_ROUND);
        this.dynamicRouter = dynamicRouter;
        this.dynamicRouter.subscribeChannel(CompleteSwapDiceToolMessage.class, this);
        IntStream.range(0, MAX_ROUND).forEach(v -> roundDice.add(new ArrayList<>()));
    }

    /**
     * Gets the available colors.
     *
     * @return iterator of current dice on round track
     */
    public List<Colors> getAvailableColors() {
        List<Colors> colorList = new ArrayList<>();
        roundDice.forEach(list -> list.stream().filter(dice -> !colorList.contains(dice.getColor())).forEach(dice -> colorList.add(dice.getColor())));
        return colorList;
    }

    /**
     * Gets the round dice.
     *
     * @return the round dice
     */
    public List<List<Dice>> getRoundDice() {
        return roundDice;
    }

    /**
     * Gets the dice from round.
     *
     * @param color the color
     * @param round the round
     * @return the dice from round
     */
    public Dice getDiceFromRound(Colors color, int round) {
        for (Dice dice : roundDice.get(round - 1)) {
            if (dice.getColor() == color)
                return dice;
        }
        return null;
    }

    /**
     * Gets the dice.
     *
     * @param diceId the dice id
     * @return the dice
     */
    private Dice getDice(int diceId) {
        Optional<List<Dice>> diceList = roundDice.stream().filter(list -> list.stream().anyMatch(dice -> dice.getId() == diceId)).findFirst();
        if(diceList.isPresent()) {
            Optional<Dice> dice = diceList.get().stream().filter(d -> d.getId()==diceId).findFirst();
            if(dice.isPresent())
                return dice.get();
        }

        return null;
    }

    /**
     * Adds the dice.
     *
     * @param diceList - Dice to be added
     * @param round    - round reference
     */
    public void addDice(List<Dice> diceList, int round) {
        diceList.forEach(roundDice.get(round - 1)::add);
    }

    /**
     * Exchange one dice for an external one.
     *
     * @param oldDice - Dice to be removed
     * @param newDice - Dice to be added
     */
    public void exchangeDice(Dice oldDice, Dice newDice) {
        Optional<List<Dice>> diceList = roundDice.stream().filter(list -> list.contains(oldDice)).findFirst();
        if(diceList.isPresent()) {
            diceList.get().remove(oldDice);
            diceList.get().add(newDice);

            sendMessage(new RoundTrackToolResponse(new DiceResponse(CommandKeyword.ROUND_TRACK, diceList.get()), roundDice.indexOf(diceList.get())+1));
        }
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#dispatch(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void dispatch(Message message) {
        RoundTrackVisitor roundTrackVisitor = (RoundTrackVisitor) message;
        roundTrackVisitor.accept(this);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Channel#sendMessage(it.polimi.ingsw.sagrada.game.intercomm.Message)
     */
    @Override
    public void sendMessage(Message message) {
        dynamicRouter.dispatch(message);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent)
     */
    @Override
    public void visit(DiceEvent diceEvent) {
        throw new UnsupportedOperationException();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.RoundTrackMessageVisitor#visit(it.polimi.ingsw.sagrada.game.intercomm.message.tool.CompleteSwapDiceToolMessage)
     */
    @Override
    public void visit(CompleteSwapDiceToolMessage completeSwapDiceToolMessage) {
        DTO dto = new DTO();
        dto.setDice(completeSwapDiceToolMessage.getDraftDice());
        dto.setSecondDice(
                getDice(completeSwapDiceToolMessage.getSwapDiceToolMessage().getRoundTrackDiceId()));
        dto.setExchangeDraftDice(completeSwapDiceToolMessage.getDraftExchange());
        dto.setExchangeRoundTrackDice(this::exchangeDice);

        completeSwapDiceToolMessage.getSwapDiceToolMessage().getToolCard().getRule().checkRule(dto);
    }
}
