package it.polimi.ingsw.sagrada.game.cards;

import it.polimi.ingsw.sagrada.game.base.State;
import it.polimi.ingsw.sagrada.game.rules.ToolRule;

/**
 * 
 */
public class ToolCard extends Card {

	private State used;

	/**
	 * Default constructor
	 */
	public ToolCard(int id, ToolRule rule) {
		super(id, rule);
		used=State.INACTIVE;
	}

	public State getUsed() {
		return used;
	}
	public void setUsed() {
		used=State.ACTIVE;
	}
}