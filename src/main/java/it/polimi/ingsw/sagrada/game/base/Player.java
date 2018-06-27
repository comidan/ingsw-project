package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.playables.Window;


/**
 * Player model class
 */
public class Player {

    private String id;

    private ObjectiveCard privateObjectiveCard;

    private Window window;

    private boolean connected;

    private int tokens;

    /**
     * Instantiates a new player
     *
     * @param id username
     */
    public Player(String id) {
        this.id = id;
        this.connected = true;
    }

    /**
     * Sets this player's window
     *
     * @param window the new window
     */
    public void setWindow(Window window) {
        this.window = window;
        tokens = window.getTokenNumber();
    }

    public int getTokens() {
        return tokens;
    }

    public void spendToken(int amount) {
        tokens -= amount;
    }

    /**
     * Gets this player's window
     *
     * @return the window
     */
    public Window getWindow() {
        return this.window;
    }

    /**
     * Sets this player's private objective card
     *
     * @param privateObjectiveCard the new private objective card
     */
    public void setPrivateObjectiveCard(ObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    /**
     * Gets this player's private objective card.
     *
     * @return the private objective card
     */
    public ObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    /**
     * Gets username
     *
     * @return username
     */
    public String getId() {
        return id;
    }

    /**
     * Checks if is connected
     *
     * @return true, if is connected
     */
    public boolean isConnected() {
        return connected;
    }
}