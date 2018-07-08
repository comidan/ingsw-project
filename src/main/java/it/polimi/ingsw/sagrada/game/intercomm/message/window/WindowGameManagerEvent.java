package it.polimi.ingsw.sagrada.game.intercomm.message.window;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.BaseGameVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.Window;



/**
 * The Class WindowGameManagerEvent.
 */
public class WindowGameManagerEvent implements Message, BaseGameVisitor {

    /** The id player. */
    private String idPlayer;
    
    /** The window. */
    private Window window;

     /**
      * Instantiates a new window game manager event.
      *
      * @param idPlayer the id player
      * @param window the window
      */
     public WindowGameManagerEvent(String idPlayer, Window window) {
         this.idPlayer = idPlayer;
         this.window = window;
     }

    /**
     * Gets the id player.
     *
     * @return the id player
     */
    public String getIdPlayer() {
        return idPlayer;
    }

    /**
     * Gets the window.
     *
     * @return the window
     */
    public Window getWindow() {
        return window;
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#getType()
     */
    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.Message#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor)
     */
    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    /**
     * Visit.
     *
     * @param baseGameMessageVisitor the visitor
     */
    @Override
    public void accept(BaseGameMessageVisitor baseGameMessageVisitor) {
        baseGameMessageVisitor.visit(this);
    }
}
