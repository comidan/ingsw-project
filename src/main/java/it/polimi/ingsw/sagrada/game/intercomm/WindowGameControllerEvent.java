package it.polimi.ingsw.sagrada.game.intercomm;

import it.polimi.ingsw.sagrada.game.playables.Window;

public class WindowGameControllerEvent implements Message{

    private int idPlayer;
    private Window window;

     public WindowGameControllerEvent(int idPlayer, Window window) {
         this.idPlayer = idPlayer;
         this.window = window;
     }

    public int getIdPlayer() {
        return idPlayer;
    }

    public Window getWindow() {
        return window;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
