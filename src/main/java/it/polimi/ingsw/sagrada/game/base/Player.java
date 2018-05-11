package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.cards.ToolCard;
import it.polimi.ingsw.sagrada.game.cards.ToolManager;
import it.polimi.ingsw.sagrada.game.playables.DiceController;
import it.polimi.ingsw.sagrada.game.playables.Token;
import it.polimi.ingsw.sagrada.game.playables.Window;

import java.util.*;

/**
 *
 */
public class Player {

    private ObjectiveCard privateObjectiveCard;
    private Window window;
    private ToolManager toolManager;
    private GameController gameController;
    private DiceController diceController;

    /**
     * Default constructor
     */
    public Player(GameController gameController, ToolManager toolManager, DiceController diceController) {
        this.gameController = gameController;
        this.toolManager = toolManager;
        this.diceController = diceController;
    }

    /**
     * @param tokens - tools buying player's tokens
     * @return
     */
    public ToolCard selectTool(List<Token> tokens) {
        // TODO implement here
        return null;
    }

    public void setPrivateObjectiveCard(ObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return this.window;
    }

    public ObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }
}