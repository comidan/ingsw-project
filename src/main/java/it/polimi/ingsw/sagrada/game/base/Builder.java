package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.rules.Rule;

import java.util.function.Function;

/**
 * 
 */
public abstract class Builder<T extends Rule> {

	protected Function<Function, T> builder;

	/**
	 * @return returns built object
	 */
	public abstract T build();

}