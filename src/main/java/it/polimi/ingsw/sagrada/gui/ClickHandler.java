package it.polimi.ingsw.sagrada.gui;

public class ClickHandler {

    ClickedObject clickedObject;
    DraftView draft;
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

    public DiceView clickCallbackCell(){
        draft.getDraft().remove(clickedObject.getClickedDice());
        return clickedObject.getClickedDice();
    }
}
