package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.Rule;

/**
 * The Class Card.
 */
public abstract class Card {

	/** The id. */
	private int id;
	
	/** The rule. */
	private Rule rule;

	/**
	 * Instantiates a new card.
	 *
	 * @param id the id
	 * @param rule the rule
	 */
	public Card(int id, Rule rule) {
		this.id = id;
		this.rule = rule;
	}

	/**
	 * Gets the rule.
	 *
	 * @return get card's rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

}