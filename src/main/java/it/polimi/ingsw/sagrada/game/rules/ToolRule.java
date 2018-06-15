package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.utility.DTO;

import java.util.function.Function;


/**
 * The Class ToolRule.
 */
public class ToolRule extends Rule<DTO, ErrorType> {


	/** The function. */
	private Function<DTO, ErrorType> function;
	
	/**
	 * Instantiates a new tool rule.
	 *
	 * @param function - building object's constructor
	 */
	ToolRule(final Function function) {
		this.function = function;
	}

	/**
	 * Builder.
	 *
	 * @return builder object
	 */
	public static ToolBuilder<ToolRule> builder() {
		return new ToolBuilder<>();
	}

	/**
	 * Check rule.
	 *
	 * @param dto - data transfer object containing action data
	 * @return the error type
	 */
	@Override
	public ErrorType checkRule(DTO dto) {
		return function.apply(dto);
	}

}