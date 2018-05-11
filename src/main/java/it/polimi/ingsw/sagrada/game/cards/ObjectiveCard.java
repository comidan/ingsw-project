package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;

/**
 * 
 */
public class ObjectiveCard extends Card {
	private String name;
	private CardType type;

	public ObjectiveCard(int id, String name, ObjectiveRule rule) {
		super(id, rule);
		this.name=name;
		type = rule.getType();
	}

	public  ObjectiveRule getRule() {
		return (ObjectiveRule) super.getRule();
	}

	public CardType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "Id: "+this.getId()+", Name: "+name;
	}
}