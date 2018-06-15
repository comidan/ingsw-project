package it.polimi.ingsw.sagrada.game.rules;


/**
 * The Class Rule.
 *
 * @param <P> the generic type
 * @param <R> the generic type
 */
public abstract class Rule<P, R> {

	/** The id. */
	private int id;

	/**
	 * Gets the rule id.
	 *
	 * @return builder object
	 */


	public int getRuleId() {
		return id;
	}

	/**
	 * Check rule.
	 *
	 * @param t the t
	 * @return the r
	 */
	abstract R checkRule(P t);
}