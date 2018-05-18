package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.utility.DTO;

import java.util.function.Function;

/**
 * 
 */
public class ToolRule extends Rule<DTO, ErrorType> {


	private Function<DTO, ErrorType> function;
	/**
	 * @param function - building object's constructor
	 */
	ToolRule(final Function function) {
		this.function = function;
	}

	/**
	 * @return builder object
	 */
	public static ToolBuilder<ToolRule> builder() {
		return new ToolBuilder<>();
	}

	/**
	 * @param dto - data transfer object containing action data
	 */
	@Override
	public ErrorType checkRule(DTO dto) {
		return function.apply(dto);
	}

}