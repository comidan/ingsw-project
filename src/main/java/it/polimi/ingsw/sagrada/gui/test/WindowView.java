package it.polimi.ingsw.sagrada.gui.test;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


//done for now (MUST REMOVE PARAMETER WINDOW ID WHEN WINDOW MODEL IS ACTUAL MODEL)

/**
 * The Class WindowView.
 */
public class WindowView extends StackPane implements ViewUpdate {
    
    /** The window model. */
    WindowModelInterface windowModel;


    /**
     * Instantiates a new window view.
     *
     * @param windowModel the window model
     * @param windowId the window id
     * @param size the size
     */
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

    /**
     * Creates the button.
     *
     * @return the cell button
     */
    protected CellButton createButton() {
        return new CellButton();
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


}
