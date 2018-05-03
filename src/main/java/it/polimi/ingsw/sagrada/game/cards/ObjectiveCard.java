package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;

/**
 * 
 */
public class ObjectiveCard extends Card {
	private Type type;

	/**
	 * Default constructor
	 */
	public ObjectiveCard(int id, ObjectiveRule rule) {
		super(id, rule);
		//gets type from ObjectiveRule
	}
}