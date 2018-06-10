package it.polimi.ingsw.sagrada.gui.test;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


//done

public class CellButton extends StackPane implements ViewUpdate, GuiDisable {
    private ButtonControllerInterface buttonControllerInterface;
    private DiceView diceView;


    //occupied cell in my windows
    public CellButton(ButtonControllerInterface buttonControllerInterface, DiceView diceView) {
        super();
        if (buttonControllerInterface != null)
            this.setupController(buttonControllerInterface);
        if (diceView != null)
            this.setDiceView(diceView);
    }

    public CellButton(DiceView diceView) {
        this(null, diceView);

    }

    // dice in other player windows
    public CellButton() {
        this(null, null);
    }

    //empty cell in my windows
    public CellButton(ButtonControllerInterface buttonControllerInterface) {
        this(buttonControllerInterface, null);
    }

    private void setupController(ButtonControllerInterface buttonControllerInterface) {
        this.buttonControllerInterface = buttonControllerInterface;
        CellButton self = this;
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                self.buttonControllerInterface.clickCallback(self);
            }
        });
    }

    public DiceView getDiceView() {
        return diceView;
        //remove from draft
    }

    public void setDiceView(DiceView diceView) {
        this.diceView = diceView;
        this.getChildren().add(this.diceView);
    }

    @Override
    public void update() {
        if (diceView != null)
            diceView.update();
    }

    @Override
    public void disable() {
        //TODO
    }

    @Override
    public void enable() {
        //TODO
    }
}
