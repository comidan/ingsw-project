package it.polimi.ingsw.sagrada.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

//done for now (MUST REMOVE PARAMETER WINDOW ID WHEN WINDOW MODEL IS ACTUAL MODEL)

public class WindowView extends StackPane implements ViewUpdate {
    WindowModelInterface windowModel;


    public WindowView(WindowModelInterface windowModel, Integer windowId, int size) {

        this.windowModel = windowModel;

        //  TODO CHANGE TO THIS AFTER IMPLEMENTATION :  String url = windowModel.getWindowPath();
        String url = windowModel.getUrl(windowId);
        Image image = new Image(url);
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        resize(iv1, size);

        GridPane grid = new GridPane();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                grid.add(this.createButton(), i, j);
            }
        }

        this.getChildren().add(iv1);
        this.getChildren().add(grid);

    }

    protected CellButton createButton() {
        return new CellButton();
    }


    public void update() {

    }

    private void resize(ImageView iv2, int dim) {
        iv2.setFitHeight(dim);
        iv2.setFitWidth(dim);
        iv2.setPreserveRatio(true);
    }


}
