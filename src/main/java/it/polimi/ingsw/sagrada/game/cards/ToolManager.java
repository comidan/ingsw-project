package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.base.utility.Pair;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.message.dice.DiceDraftSelectionEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.game.EndTurnEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ToolGameVisitor;

import java.util.*;


/**
 * The Class ToolManager.
 */
public class ToolManager implements Channel<Message, ToolResponse>, ToolGameMessageVisitor {

	/** The tool cards. */
	private List<ToolCard> toolCards;

	private String currentToolbuyer;
	private ToolCard currentSelectedTool;

	private  Map<String, Player> players;

	private DynamicRouter dynamicRouter;

	private int cost;

	/**
	 * Default constructor.
	 *
	 * @param toolCards the tool cards
	 */
	public ToolManager(List<ToolCard> toolCards, Map<String, Player> players, DynamicRouter dynamicRouter) {
		this.toolCards = toolCards;
		this.players = players;
		this.dynamicRouter = dynamicRouter;
		this.dynamicRouter.subscribeChannel(ToolEvent.class, this);
		this.dynamicRouter.subscribeChannel(EndTurnEvent.class, this);
		this.dynamicRouter.subscribeChannel(DiceDraftSelectionEvent.class, this);
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

	@Override
	public void dispatch(Message message) {
		ToolGameVisitor toolGameVisitor = (ToolGameVisitor) message;
		System.out.println("Received toolGameVisitor");
		toolGameVisitor.accept(this);
	}

	@Override
	public void sendMessage(ToolResponse message) {
		dynamicRouter.dispatch(message);
	}

	@Override
	public void visit(EndTurnEvent endTurnEvent) {
		currentToolbuyer = "";
		currentToolbuyer = null;
	}

	@Override
	public void visit(ToolEvent toolEvent) {
		boolean result = canBuyTool(toolEvent.getToolId(), players.get(toolEvent.getPlayerId()));
		sendMessage(new ToolResponse(result, toolEvent.getPlayerId(), 1));
	}

    @Override
    public void visit(DiceDraftSelectionEvent diceDraftSelectionEvent) {
        //controlla che il messaggio arrivi in un momento sensato TO-DO
    }
}