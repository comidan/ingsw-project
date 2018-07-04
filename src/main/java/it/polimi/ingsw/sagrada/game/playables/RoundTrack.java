package it.polimi.ingsw.sagrada.game.playables;

import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.base.utility.DTO;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceResponse;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.CompleteSwapDiceToolMessage;
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
public class RoundTrack implements Channel<Message, DiceResponse>, RoundTrackMessageVisitor {

    private static final int MAX_ROUND = 10;

    /** The round dice. */
    private List<List<Dice>> roundDice;

    private DynamicRouter dynamicRouter;

    /**
     * Instantiates a new round track.
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

    private Dice getDice(int diceId, int round) {
        for(Dice dice:roundDice.get(round-1)) {
            if(diceId==dice.getId())
                return dice;
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
     * Exchange one dice for an external one
     *
     * @param oldDice - Dice to be removed
     * @param newDice - Dice to be added
     */
    public void exchangeDice(Dice oldDice, Dice newDice) {
        Optional<List<Dice>> diceList = roundDice.stream().filter(list -> list.contains(oldDice)).findFirst();
        if(diceList.isPresent()) {
            diceList.get().remove(oldDice);
            diceList.get().add(newDice);
            sendMessage(new DiceResponse(CommandKeyword.ROUND_TRACK, diceList.get()));
        }
    }

    @Override
    public void dispatch(Message message) {
        RoundTrackVisitor roundTrackVisitor = (RoundTrackVisitor) message;
        System.out.println("Received roundTrackVisitor");
        roundTrackVisitor.accept(this);
    }

    @Override
    public void sendMessage(DiceResponse message) {
        dynamicRouter.dispatch(message);
    }

    @Override
    public void visit(DiceEvent diceEvent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(CompleteSwapDiceToolMessage completeSwapDiceToolMessage) {
        DTO dto = new DTO();
        dto.setDice(completeSwapDiceToolMessage.getDraftDice());
        dto.setSecondDice(
                getDice(completeSwapDiceToolMessage.getSwapDiceToolMessage().getRoundTrackDiceId(),
                        completeSwapDiceToolMessage.getSwapDiceToolMessage().getRoundNumber()));
        dto.setExchangeDraftDice(completeSwapDiceToolMessage.getDraftExchange());
        dto.setExchangeRoundTrackDice(this::exchangeDice);

        completeSwapDiceToolMessage.getSwapDiceToolMessage().getToolCard().getRule().checkRule(dto);
    }
}
