package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.ToolRule;


/**
 * The Class ToolCard.
 */
public class ToolCard extends Card {
	
	/** The usage. */
	private Usage usage;
	
	/** The name. */
	private String name;

	/**
	 * Default constructor.
	 *
	 * @param id the id
	 * @param name the name
	 * @param rule the rule
	 */
	public ToolCard(int id, String name, ToolRule rule) {
		super(id, rule);
		this.usage = Usage.NEW;
		this.name = name;
	}

	/**
	 * Gets the usage.
	 *
	 * @return the usage
	 */
	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage u) { usage=u; }

	@Override
	public ToolRule getRule() {
		return (ToolRule) super.getRule();
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}