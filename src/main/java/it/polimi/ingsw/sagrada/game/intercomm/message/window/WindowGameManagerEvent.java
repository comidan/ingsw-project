package it.polimi.ingsw.sagrada.game.intercomm.message.window;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.Window;

public class WindowGameManagerEvent implements Message {

    private String idPlayer;
    private Window window;

     public WindowGameManagerEvent(String idPlayer, Window window) {
         this.idPlayer = idPlayer;
         this.window = window;
     }

    public String getIdPlayer() {
        return idPlayer;
    }

    public Window getWindow() {
        return window;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }
}
