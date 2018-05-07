package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.CardController;
import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.playables.DiceController;
import it.polimi.ingsw.sagrada.game.playables.RoundTrack;
import it.polimi.ingsw.sagrada.game.playables.ScoreTrack;

import java.util.List;
import java.util.NoSuchElementException;

import static it.polimi.ingsw.sagrada.game.base.StateGameController.*;

/**
 * 
 */

public class GameController {

	private Player[] players;
	private DiceController diceController;
	private RoundTrack roundTrack;
	private ScoreTrack scoreTrack;
	private CardController cardController;
	private StateGameController state;
	private StateIterator stateIterator = StateIterator.getInstance();


	/**
	 * Default constructor
	 */
	public GameController() {

	}

	public void setupGame() {
		while(stateIterator.hasNext()&&state!=DEAL_PUBLIC_OBJECTIVE) {
			state=stateIterator.next();
			switch (state) {
				case DEAL_PRIVATE_OBJECTIVE: dealPrivateObjectiveState(); break;
				case DEAL_WINDOWS: dealWindowsState(); break;
				case DEAL_TOOL: dealToolState(); break;
				case DEAL_PUBLIC_OBJECTIVE: dealPublicObjectiveState(); break;
				default: throw new NoSuchElementException();
			}
		}
	}

	private void dealPrivateObjectiveState() {
		List<ObjectiveCard> privateObjective;
		privateObjective = cardController.dealPrivateObjective(players.length);
		for(int i=0; i<players.length; i++) {
			players[i].setPrivateObjectiveCard(privateObjective.get(i));
		}
	}

	private void dealWindowsState() {
		// TODO implement here
	}

	private void dealToolState() {
		// TODO implement here
	}

	private void dealPublicObjectiveState() {
		// TODO implement here
	}

	public void playRound() {
		// TODO implement here
	}


}