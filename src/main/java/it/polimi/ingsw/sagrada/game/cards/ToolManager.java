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
		if(instance==null) return new ToolManager(toolCards);
		else return instance;
	}


	/**
	 * @param tokens - available player's tokens
	 * @return selected tool card
	 */
	public ToolCard getTool(List<Token> tokens) {
		// TODO implement here
		return null;
	}

}