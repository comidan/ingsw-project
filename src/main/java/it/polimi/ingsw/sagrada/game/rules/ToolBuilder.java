package it.polimi.ingsw.sagrada.game.rules;

import it.polimi.ingsw.sagrada.game.base.Builder;

import java.util.function.Function;

/**
 * 
 */
public class ToolBuilder<T> implements Builder {

	/**
	 * @param builder - Reference to constructor of the object being built
	 */
	public ToolBuilder(final Function builder) {
		// TODO implement here
	}

	@Override
	public Rule build() {
		//TODO implement here
		return null;
	}

	/**
	 * @return this ToolBuilder with an updated tool feature
	 */
	public ToolBuilder<T> setSomeFeature() {
		// TODO implement here
		return null;
	}

}