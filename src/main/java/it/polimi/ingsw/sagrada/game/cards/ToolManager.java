package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.utility.Colors;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceDraftSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackColorSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceRoundTrackSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.*;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameVisitor;
import it.polimi.ingsw.sagrada.network.CommandKeyword;

import java.util.*;
import java.util.function.Consumer;


/**
 * The Class ToolManager.
 */
public class ToolManager implements Channel<Message, Message>, ToolGameMessageVisitor {

	/** The tool cards. */
	private List<ToolCard> toolCards;

	private String currentToolbuyer;

	private ToolCard currentSelectedTool;

	private Map<String, Player> players;

	private DynamicRouter dynamicRouter;

	private Consumer<Integer> ignoreValueSet;

	private Consumer<Integer> ignoreColorSet;

	private int cost;



	private int diceCounter;

	private Colors colorConstraint;

	private DiceRoundTrackSelectionEvent roundTrackSelectionEvent;
	private DiceDraftSelectionEvent draftSelectionEvent;

	/**
	 * Default constructor.
	 *
	 * @param toolCards the tool cards
	 */
	public ToolManager(List<ToolCard> toolCards, Map<String, Player> players, Consumer<Integer> ignoreValueSet, Consumer<Integer> ignoreColorSet, DynamicRouter dynamicRouter) {
		this.toolCards = toolCards;
		this.players = players;
		this.dynamicRouter = dynamicRouter;
		this.ignoreValueSet = ignoreValueSet;
		this.ignoreColorSet = ignoreColorSet;
		diceCounter = 0;
		this.dynamicRouter.subscribeChannel(ToolEvent.class, this);
		this.dynamicRouter.subscribeChannel(EndTurnEvent.class, this);
		this.dynamicRouter.subscribeChannel(DiceDraftSelectionEvent.class, this);
		this.dynamicRouter.subscribeChannel(DiceRoundTrackSelectionEvent.class, this);
		this.dynamicRouter.subscribeChannel(DiceEvent.class, this);
		this.dynamicRouter.subscribeChannel(DiceRoundTrackColorSelectionEvent.class, this);
	}

	public ToolCard getCurrentSelectedTool() {
		return currentSelectedTool;
	}
	
	/**
	 * Can buy tool.
	 *
	 * @param id the id
	 * @param player - player that wants to buy the tool
	 * @return selected tool card
	 */
	private boolean canBuyTool(int id, Player player) {
		cost=0;

		for (ToolCard card : toolCards) {
			if (card.getId() == id) {
				if (card.getUsage() == Usage.NEW) cost = 1;
				else cost = 2;

				if (player.getTokens() >= cost) {
					card.setUsage(Usage.USED);
					player.spendToken(cost);
					currentToolbuyer = player.getId();
					currentSelectedTool = card;
					return true;
				} else {
					cost=0;
					return false;
				}
			}
		}
		return false;
	}

	private void resetTool() {
		currentToolbuyer = "";
		currentToolbuyer = null;
		diceCounter = 0;
		colorConstraint = null;
		roundTrackSelectionEvent = null;
		draftSelectionEvent = null;
	}

	private void swapDiceDraftRoundTrack() {
		System.out.println("///////////////ToolManager///////////////////"+draftSelectionEvent);
		System.out.println("///////////////ToolManager///////////////////"+roundTrackSelectionEvent);
		if(draftSelectionEvent!=null && roundTrackSelectionEvent!=null) {
			System.out.println("///////////////ToolManager ha inviato il messaggio///////////////////");
			sendMessage(new SwapDiceToolMessage(
					currentSelectedTool,
					draftSelectionEvent.getIdDice(),
					roundTrackSelectionEvent.getDiceId(),
					roundTrackSelectionEvent.getTurn()));
			resetTool();
		}
	}

	@Override
	public void dispatch(Message message) {
		ToolGameVisitor toolGameVisitor = (ToolGameVisitor) message;
		System.out.println("Received toolGameVisitor");
		toolGameVisitor.accept(this);
	}

	@Override
	public void sendMessage(Message message) {
		dynamicRouter.dispatch(message);
	}

	@Override
	public void visit(EndTurnEvent endTurnEvent) {
		resetTool();
	}

	@Override
	public void visit(ToolEvent toolEvent) {
		boolean result = canBuyTool(toolEvent.getToolId(), players.get(toolEvent.getPlayerId()));
		sendMessage(new ToolResponse(result, toolEvent.getPlayerId(), cost, toolEvent.getToolId()));
		if(result) {
			if(currentSelectedTool.getId()==7) sendMessage(new EnableDoubleTurnToolMessage(toolEvent.getPlayerId()));
			else if(currentSelectedTool.getId()==8) sendMessage(new MoveAloneDiceToolMessage());
		}
	}

    @Override
    public void visit(DiceDraftSelectionEvent diceDraftSelectionEvent) {
		System.out.println("---ToolManager current tool--- "+currentSelectedTool.getId());
		int id = currentSelectedTool.getId();
		if(id==0 || id==5 || id==9) {
			sendMessage(new ChangeDiceValueToolMessage(currentSelectedTool, diceDraftSelectionEvent.getIdDice()));
			resetTool();
		}
		else if(id==4) {
			draftSelectionEvent = diceDraftSelectionEvent;
			swapDiceDraftRoundTrack();
		}
		else if(id==6) {
			sendMessage(new RollAllDiceToolMessage(currentSelectedTool));
			resetTool();
		}
    }

	@Override
	public void visit(DiceRoundTrackSelectionEvent diceRoundTrackSelectionEvent) {
		int id = currentSelectedTool.getId();
		System.out.println("---ToolManager current tool--- "+currentSelectedTool.getId());
		if(id==4) {
			roundTrackSelectionEvent = diceRoundTrackSelectionEvent;
			swapDiceDraftRoundTrack();
		}
	}

	@Override
	public void visit(DiceEvent diceEvent) {
		if(diceEvent.getSrc().equals(CommandKeyword.WINDOW)) {
			int id = currentSelectedTool.getId();
			if(id == 1) {
				sendMessage(new MoveDiceWindowToolMessage(
						currentSelectedTool,
						diceEvent.getIdPlayer(),
						diceEvent.getIdDice(),
						diceEvent.getPosition(),
						ignoreColorSet
				));
				resetTool();
			} else if(id == 2) {
				sendMessage(new MoveDiceWindowToolMessage(
						currentSelectedTool,
						diceEvent.getIdPlayer(),
						diceEvent.getIdDice(),
						diceEvent.getPosition(),
						ignoreValueSet
				));
				resetTool();
			} else if(id == 3) {
				diceCounter++;
				if(diceCounter<2) {
					sendMessage(new EnableWindowToolResponse(currentToolbuyer, currentSelectedTool.getId()));
					sendMessage(new MoveDiceToolMessage(
							currentSelectedTool,
							diceEvent.getIdPlayer(),
							diceEvent.getIdDice(),
							diceEvent.getPosition()));
				} else {
					sendMessage(new MoveDiceToolMessage(
							currentSelectedTool,
							diceEvent.getIdPlayer(),
							diceEvent.getIdDice(),
							diceEvent.getPosition()));
					diceCounter = 0;
				}
			} else if(id == 11) {
				diceCounter++;
				if(diceCounter<2) {
					sendMessage(new EnableWindowToolResponse(currentToolbuyer, currentSelectedTool.getId()));
					sendMessage(new ColorConstraintToolMessage(currentSelectedTool, diceEvent, colorConstraint));
				} else {
					sendMessage(new ColorConstraintToolMessage(currentSelectedTool, diceEvent, colorConstraint));
					diceCounter = 0;
				}
			}
		}
	}

	@Override
	public void visit(DiceRoundTrackColorSelectionEvent diceRoundTrackColorSelectionEvent) {
		int id = currentSelectedTool.getId();
		if(id == 11) {
			colorConstraint=diceRoundTrackColorSelectionEvent.getConstraint();
		}
	}
}