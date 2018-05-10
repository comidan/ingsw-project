package it.polimi.ingsw.sagrada.game.rules;

/**
 * 
 */
public abstract class Rule<P, R> {

	private int id;

	/**
	 * @return builder object
	 */


	public int getRuleId() {
		return id;
	}

	abstract R checkRule(P t);
}