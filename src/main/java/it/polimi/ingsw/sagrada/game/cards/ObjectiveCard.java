package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;

/**
 * 
 */
public class ObjectiveCard extends Card {
	private CardType type;

	/**
	 * Default constructor
	 */
	public ObjectiveCard(int id, ObjectiveRule rule) {
		super(id, rule);
		type = rule.getType();
	}

	public CardType getType() {
		return type;
	}


}