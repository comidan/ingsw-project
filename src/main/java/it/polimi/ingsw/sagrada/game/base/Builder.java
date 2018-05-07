package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.rules.Rule;

/**
 * 
 */
public interface Builder<T extends Rule> {

	/**
	 * @return returns built object
	 */
	T build();

}