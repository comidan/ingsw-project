package it.polimi.ingsw.sagrada.game.intercomm.message.window;

import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.visitor.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;



/**
 * The Class WindowEvent.
 */
public class WindowEvent implements Message, ActionVisitor {

    /** The id player. */
    private String idPlayer;
    
    /** The id window. */
    private int idWindow;
    
    /** The window side. */
    private WindowSide windowSide;

    /**
     * Instantiates a new window event.
     *
     * @param idPlayer the id player
     * @param idWindow the id window
     * @param windowSide the window side
     */
    public WindowEvent(String idPlayer, int idWindow, WindowSide windowSide) {
        this.idPlayer = idPlayer;
        this.idWindow = idWindow;
        this.windowSide = windowSide;
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
     * Gets the id window.
     *
     * @return the id window
     */
    public int getIdWindow() {
        return idWindow;
    }

    /**
     * Gets the window side.
     *
     * @return the window side
     */
    public WindowSide getWindowSide() {
        return windowSide;
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

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionVisitor#accept(it.polimi.ingsw.sagrada.game.intercomm.visitor.ActionMessageVisitor)
     */
    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }
}
