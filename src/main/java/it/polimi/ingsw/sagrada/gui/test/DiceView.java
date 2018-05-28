package it.polimi.ingsw.sagrada.gui.test;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

//done

public class DiceView extends StackPane implements ViewUpdate {

    public DiceView(DiceModel diceModel) {
        ImageView imv = new ImageView(new Image(diceModel.getUrl()));
        resize(imv, 50);
        this.getChildren().add(imv);
    }

    public void update() {
    }


    private void resize(ImageView iv2, int dim) {
        iv2.setFitHeight(dim);
        iv2.setFitWidth(dim);
        iv2.setPreserveRatio(true);
    }

}
