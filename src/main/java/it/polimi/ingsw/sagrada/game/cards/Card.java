package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.Rule;


/**
 * 
 */
public abstract class Card {

	private int id;
	private Rule rule;

	public Card(int id, Rule rule) {
		this.id = id;
		this.rule = rule;
	}

	/**
	 * @return get card's rule
	 */
	public Rule getRule() {
		return rule;
	}

	public int getId() {
		return id;
	}

}