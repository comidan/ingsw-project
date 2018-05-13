package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.playables.Token;

import java.util.*;

/**
 * 
 */
public class ToolManager {

	private static ToolManager instance;
	private List<ToolCard> toolCards;


	/**
	 * Default constructor
	 */
	private ToolManager(List<ToolCard> toolCards) {
		this.toolCards = toolCards;
	}

	public static ToolManager getInstance(List<ToolCard> toolCards) {
		if(instance==null) instance = new ToolManager(toolCards);
		return instance;
	}


	/**
	 * @param tokens - available player's tokens
	 * @return selected tool card
	 */
	public boolean canBuyTool(int id, List<Token> tokens) {
		ToolCard card;
		int cost;
		int i=0;

		while(i<toolCards.size()) {
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