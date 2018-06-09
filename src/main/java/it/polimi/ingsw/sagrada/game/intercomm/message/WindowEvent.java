package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.ActionMessageVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.ActionVisitor;
import it.polimi.ingsw.sagrada.game.intercomm.Message;
import it.polimi.ingsw.sagrada.game.intercomm.MessageVisitor;
import it.polimi.ingsw.sagrada.game.playables.WindowSide;

public class WindowEvent implements Message, ActionVisitor {

    private String idPlayer;
    private int idWindow;
    private WindowSide windowSide;

    public WindowEvent(String idPlayer, int idWindow, WindowSide windowSide) {
        this.idPlayer = idPlayer;
        this.idWindow = idWindow;
        this.windowSide = windowSide;
    }

    public String getIdPlayer() {
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

    @Override
    public void accept(MessageVisitor messageVisitor) {
        messageVisitor.visit(this);
    }

    @Override
    public String accept(ActionMessageVisitor actionMessageVisitor) {
        return actionMessageVisitor.visit(this);
    }
}
