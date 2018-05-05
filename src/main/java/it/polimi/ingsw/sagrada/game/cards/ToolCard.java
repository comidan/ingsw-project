package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.ToolRule;

/**
 * 
 */
public class ToolCard extends Card {
	private Usage usage;

	/**
	 * Default constructor
	 */
	public ToolCard(int id, ToolRule rule) {
		super(id, rule);
		usage = Usage.NEW;
	}

	public Usage getUsage() {
		return  usage;
	}


}