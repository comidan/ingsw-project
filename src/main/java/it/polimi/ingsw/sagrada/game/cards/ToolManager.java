package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.playables.Token;

import java.util.*;


/**
 * The Class ToolManager.
 */
public class ToolManager {

	/** The instance. */
	private static ToolManager instance;
	
	/** The tool cards. */
	private List<ToolCard> toolCards;


	/**
	 * Default constructor.
	 *
	 * @param toolCards the tool cards
	 */
	private ToolManager(List<ToolCard> toolCards) {
		this.toolCards = toolCards;
	}

	/**
	 * Gets the single instance of ToolManager.
	 *
	 * @param toolCards the tool cards
	 * @return single instance of ToolManager
	 */
	public static ToolManager getInstance(List<ToolCard> toolCards) {
		if(instance==null) instance = new ToolManager(toolCards);
		return instance;
	}


	/**
	 * Can buy tool.
	 *
	 * @param id the id
	 * @param tokens - available player's tokens
	 * @return selected tool card
	 */
	public boolean canBuyTool(int id, List<Token> tokens) {
		ToolCard card;
		int cost;
		int i = 0;

		while(i < toolCards.size()) {
			card = toolCards.get(i);
			if(card.getId() == id) {
				if(card.getUsage()==Usage.NEW) cost=1;
				else cost=2;

				return tokens.size()>=cost;
			}
			i++;
		}
		return false;
	}

}