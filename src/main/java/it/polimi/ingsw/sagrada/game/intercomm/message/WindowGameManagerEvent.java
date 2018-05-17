package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.playables.Window;

public class WindowGameManagerEvent implements Message {

    private int idPlayer;
    private Window window;

     public WindowGameManagerEvent(int idPlayer, Window window) {
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
