package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.rules.Rule;



/**
 * Builder design pattern interface.
 *
 * @param <T> the generic type
 */
public interface Builder<T extends Rule> {

	/**
	 * Builds the generic T type.
	 *
	 * @return returns built object of type T
	 */
	T build();

}