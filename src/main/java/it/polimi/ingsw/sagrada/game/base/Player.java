package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.playables.Window;

/**
 *
 */
public class Player {

    private int id;
    private ObjectiveCard privateObjectiveCard;
    private Window window;

    public Player(int id) {
        this.id = id;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return this.window;
    }

    public void setPrivateObjectiveCard(ObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public ObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    public int getId() {
        return id;
    }
}