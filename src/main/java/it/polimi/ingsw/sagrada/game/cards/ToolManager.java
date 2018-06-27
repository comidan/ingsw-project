package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.intercomm.Channel;
import it.polimi.ingsw.sagrada.game.intercomm.DynamicRouter;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolEvent;
import it.polimi.ingsw.sagrada.game.intercomm.message.tool.ToolResponse;

import java.util.*;
import java.util.function.Function;


/**
 * The Class ToolManager.
 */
public class ToolManager implements Channel<ToolEvent, ToolResponse> {
	
	/** The tool cards. */
	private List<ToolCard> toolCards;

	private Function<String, Integer> getNumberToken;

	private DynamicRouter dynamicRouter;

	/**
	 * Default constructor.
	 *
	 * @param toolCards the tool cards
	 */
	public ToolManager(List<ToolCard> toolCards, Function<String, Integer>getNumberToken, DynamicRouter dynamicRouter) {
		this.toolCards = toolCards;
		this.getNumberToken = getNumberToken;
		this.dynamicRouter = dynamicRouter;
		this.dynamicRouter.subscribeChannel(ToolEvent.class, this);
	}
	
	/**
	 * Can buy tool.
	 *
	 * @param id the id
	 * @param numToken - available player's tokens
	 * @return selected tool card
	 */
	private boolean canBuyTool(int id, int numToken) {
		ToolCard card;
		int cost;
		int i = 0;

		while(i < toolCards.size()) {
			card = toolCards.get(i);
			if(card.getId() == id) {
				if(card.getUsage()==Usage.NEW) cost=1;
				else cost=2;

				return numToken>=cost;
			}
			i++;
		}
		return false;
	}

	@Override
	public void dispatch(ToolEvent message) {
		boolean result = canBuyTool(message.getToolId(), getNumberToken.apply(message.getPlayerId()));
		sendMessage(new ToolResponse(result, message.getPlayerId()));
	}

	@Override
	public void sendMessage(ToolResponse message) {
		dynamicRouter.dispatch(message);
	}
}