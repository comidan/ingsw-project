package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.playables.WindowSide;

public class WindowEvent implements Message {

    private int idPlayer;
    private int idWindow;
    private WindowSide windowSide;

    public WindowEvent(int idPlayer, int idWindow, WindowSide windowSide) {
        this.idPlayer = idPlayer;
        this.idWindow = idWindow;
        this.windowSide = windowSide;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public int getIdWindow() {
        return idWindow;
    }

    public WindowSide getWindowSide() {
        return windowSide;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
