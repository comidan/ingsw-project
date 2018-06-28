package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.base.Player;
import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;

import java.util.*;


/**
 * The Class ToolManager.
 */
public class ToolManager implements Channel<ToolEvent, ToolResponse> {
	
	/** The tool cards. */
	private List<ToolCard> toolCards;

	private  Map<String, Player> players;

	private DynamicRouter dynamicRouter;

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
	}
	
	/**
	 * Can buy tool.
	 *
	 * @param id the id
	 * @param player - player that wants to buy the tool
	 * @return selected tool card
	 */
	private boolean canBuyTool(int id, Player player) {
		int cost;

		for (ToolCard card : toolCards) {
			if (card.getId() == id) {
				if (card.getUsage() == Usage.NEW) cost = 1;
				else cost = 2;

				if (player.getTokens() >= cost) {
					card.setUsage(Usage.USED);
					player.spendToken(cost);
					return true;
				} else return false;
			}
		}
		return false;
	}

	@Override
	public void dispatch(ToolEvent message) {
		boolean result = canBuyTool(message.getToolId(), players.get(message.getPlayerId()));
		sendMessage(new ToolResponse(result, message.getPlayerId()));
	}

	@Override
	public void sendMessage(ToolResponse message) {
		dynamicRouter.dispatch(message);
	}
}