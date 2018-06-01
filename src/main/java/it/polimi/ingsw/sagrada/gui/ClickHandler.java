package it.polimi.ingsw.sagrada.gui;

import java.util.List;

public class ClickHandler {

    private ClickedObject clickedObject;
    private DraftView draft;
    private static ClickHandler clickHandler;

    private ClickHandler(ClickedObject clickedObject){
        this.clickedObject = clickedObject;

    }

    public static ClickHandler getDiceButtonController (ClickedObject clickedObject){
        if(clickHandler == null)
            clickHandler = new ClickHandler(clickedObject);
        return clickHandler;
    }

    public void setDraft(DraftView draft){
        this.draft = draft;
    }

    public void clickCallbackDice(DiceView diceView){
        clickedObject.setClickedDice(diceView);

    }

    public DiceView clickCallbackCell() {
        if (clickedObject != null) {
            List<DiceView> draftList = draft.getDraft();
            for (int i = 0; i < draftList.size(); i++) {
                if (draftList.get(i).equals(clickedObject.getClickedDice()))
                {
                    draftList.remove(draftList.get(i));
                    return clickedObject.getClickedDice();
                }

            }
           return null;
        }
        else return null;
    }
}
