package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;

import java.util.function.Function;

/**
 * 
 */
public abstract class Rule {

	private Function function;
	private int id;

	/**
	 * @return
	 */
	public Builder<Rule> builder() {
		// TODO implement here
		return null;
	}
}