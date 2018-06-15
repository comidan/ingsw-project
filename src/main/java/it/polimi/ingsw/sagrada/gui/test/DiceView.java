package it.polimi.ingsw.sagrada.gui.test;


import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


//done

/**
 * The Class DiceView.
 */
public class DiceView extends StackPane implements ViewUpdate {

    /** The value. */
    private Constraint value;

    /**
     * Instantiates a new dice view.
     *
     * @param diceModel the dice model
     */
    public DiceView(DiceModel diceModel) {
        ImageView imv = new ImageView(new Image(diceModel.getUrl()));
        resize(imv, 50);
        this.getChildren().add(imv);
    }

    /* (non-Javadoc)
     * @see it.polimi.ingsw.sagrada.gui.test.ViewUpdate#update()
     */
    public void update() {
    }


    /**
     * Resize.
     *
     * @param iv2 the iv 2
     * @param dim the dim
     */
    private void resize(ImageView iv2, int dim) {
        iv2.setFitHeight(dim);
        iv2.setFitWidth(dim);
        iv2.setPreserveRatio(true);
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Constraint getValue() {
        return value;
    }
}
