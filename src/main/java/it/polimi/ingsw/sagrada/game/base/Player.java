package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.cards.ObjectiveCard;
import it.polimi.ingsw.sagrada.game.playables.Window;



/**
 * Player model class.
 */
public class Player {

    /** The id. */
    private String id;

    /** The private objective card. */
    private ObjectiveCard privateObjectiveCard;

    /** The window. */
    private Window window;

    /** The connected. */
    private boolean connected;

    /** The is turn played. */
    private boolean isTurnPlayed;

    /** The tokens. */
    private int tokens;

    /**
     * Instantiates a new player.
     *
     * @param id username
     */
    public Player(String id) {
        this.id = id;
        this.connected = true;
        this.isTurnPlayed = false;
    }

    /**
     * Sets this player's window.
     *
     * @param window the new window
     */
    public void setWindow(Window window) {
        this.window = window;
        tokens = window.getTokenNumber();
    }

    /**
     * Gets the tokens.
     *
     * @return the tokens
     */
    public int getTokens() {
        return tokens;
    }

    /**
     * Spend token.
     *
     * @param amount the amount
     */
    public void spendToken(int amount) {
        tokens -= amount;
    }

    /**
     * Gets this player's window.
     *
     * @return the window
     */
    public Window getWindow() {
        return this.window;
    }

    /**
     * Sets this player's private objective card.
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
     * Gets username.
     *
     * @return username
     */
    public String getId() {
        return id;
    }

    /**
     * Checks if is connected.
     *
     * @return true, if is connected
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Checks if is turn played.
     *
     * @return true, if is turn played
     */
    public boolean isTurnPlayed() { return isTurnPlayed; }

    /**
     * Sets the checks if is turn played.
     *
     * @param set the new checks if is turn played
     */
    public void setIsTurnPlayed(boolean set) { isTurnPlayed=set; }
}