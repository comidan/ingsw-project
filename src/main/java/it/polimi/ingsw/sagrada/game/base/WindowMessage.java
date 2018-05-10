package it.polimi.ingsw.sagrada.game.base;

import it.polimi.ingsw.sagrada.game.playables.Window;

public class WindowMessage {
    private Window window;
    private Player player;

    public WindowMessage(Window window, Player player) {
        this.window=window;
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

    public Window getWindow() {
        return window;
    }
}
