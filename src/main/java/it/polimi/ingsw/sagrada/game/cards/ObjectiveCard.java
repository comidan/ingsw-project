package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.rules.ObjectiveRule;



/**
 * The Class ObjectiveCard.
 */
public class ObjectiveCard extends Card {
	
	/** The name. */
	private String name;
	
	/** The type. */
	private CardType type;

	/**
	 * Instantiates a new objective card.
	 *
	 * @param id the id
	 * @param name the name
	 * @param rule the rule
	 */
	public ObjectiveCard(int id, String name, ObjectiveRule rule) {
		super(id, rule);
		this.name=name;
		type = rule.getType();
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.sagrada.game.cards.Card#getRule()
	 */
	public  ObjectiveRule getRule() {
		return (ObjectiveRule) super.getRule();
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public CardType getType() {
		return type;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Id: "+this.getId()+", Name: "+name;
	}
}