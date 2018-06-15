package it.polimi.ingsw.sagrada.gui.test;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;



//done

/**
 * The Class CellButton.
 */
public class CellButton extends StackPane implements ViewUpdate, GuiDisable {
    
    /** The button controller interface. */
    private ButtonControllerInterface buttonControllerInterface;
    
    /** The dice view. */
    private DiceView diceView;


    /**
     * Instantiates a new cell button.
     *
     * @param buttonControllerInterface the button controller interface
     * @param diceView the dice view
     */
    //occupied cell in my windows
    public CellButton(ButtonControllerInterface buttonControllerInterface, DiceView diceView) {
        super();
        if (buttonControllerInterface != null)
            this.setupController(buttonControllerInterface);
        if (diceView != null)
            this.setDiceView(diceView);
    }

    /**
     * Instantiates a new cell button.
     *
     * @param diceView the dice view
     */
    public CellButton(DiceView diceView) {
        this(null, diceView);

    }

    /**
     * Instantiates a new cell button.
     */
    // dice in other player windows
    public CellButton() {
        this(null, null);
    }

    /**
     * Instantiates a new cell button.
     *
     * @param buttonControllerInterface the button controller interface
     */
    //empty cell in my windows
    public CellButton(ButtonControllerInterface buttonControllerInterface) {
        this(buttonControllerInterface, null);
    }

    /**
     * Sets the up controller.
     *
     * @param buttonControllerInterface the new up controller
     */
    private void setupController(ButtonControllerInterface buttonControllerInterface) {
        this.buttonControllerInterface = buttonControllerInterface;
        CellButton self = this;
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                self.buttonControllerInterface.clickCallback(self);
            }
        });
    }

    /**
     * Gets the dice view.
     *
     * @return the dice view
     */
    public DiceView getDiceView() {
        return diceView;
        //remove from draft
    }

    /**
     * Sets the dice view.
     *
     * @param diceView the new dice view
     */
    public void setDiceView(DiceView diceView) {
        this.diceView = diceView;
        this.getChildren().add(this.diceView);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.gui.test.ViewUpdate#update()
     */
    @Override
    public void update() {
        if (diceView != null)
            diceView.update();
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.gui.test.GuiDisable#disable()
     */
    @Override
    public void disable() {
        //TODO
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.gui.test.GuiDisable#enable()
     */
    @Override
    public void enable() {
        //TODO
    }
}
