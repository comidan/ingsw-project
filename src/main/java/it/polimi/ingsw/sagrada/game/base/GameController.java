package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.playables.DiceController;
import it.polimi.ingsw.sagrada.game.playables.RoundTrack;
import it.polimi.ingsw.sagrada.game.playables.ScoreTrack;

/**
 * 
 */

public class GameController {

	private Player[] players;
	private DiceController diceController;
	private RoundTrack roundTrack;
	private ScoreTrack scoreTrack;
	private CardController cardController;
	private StateGameController state = StateGameController.getFirstState();
	private StateIterator stateIterator = StateIterator.getInstance();


	/**
	 * Default constructor
	 */
	public GameController() {
	}

}