package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.ToolRule;

/**
 * 
 */
public class ToolCard extends Card {
	private Usage usage;
	private String name;

	/**
	 * Default constructor
	 */
	public ToolCard(int id, String name, ToolRule rule) {
		super(id, rule);
		this.usage = Usage.NEW;
		this.name = name;
	}

	public Usage getUsage() {
		return  usage;
	}

	public String getName(String name) {
		return name;
	}
}