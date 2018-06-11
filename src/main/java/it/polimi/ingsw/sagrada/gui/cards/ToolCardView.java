package it.polimi.ingsw.sagrada.gui.cards;

import it.polimi.ingsw.sagrada.gui.components.TokenView;
import it.polimi.ingsw.sagrada.gui.utils.Resizer;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.File;

public class ToolCardView extends StackPane {

    private static final String TOOL_IMAGE_ROOT_PATH = "/images/ToolImages/";
    private int id;
    private Resizer resizer;
    private int tokenNumber = 0;
    private GridPane tokenGrid;
    private Label label;

    public ToolCardView(int id) {
        tokenGrid = new GridPane();
        this.resizer = new Resizer();
        this.id = id;
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(ToolCardView.class.getResourceAsStream(TOOL_IMAGE_ROOT_PATH + Integer.toString(id) + ".jpg"), resizer.getWidthPixel(15), resizer.getHeightPixel(20), true, false));
        this.getChildren().add(imageView);
        this.getChildren().add(tokenGrid);
        label = new Label();
        this.getChildren().add(label);
    }

    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        this.setOnMouseClicked(toolClickHandler);
    }

    public int getToolId(){
        return id;
    }

    public void addToken(){
        setTokenNumber();
        for(int i = 0; i<tokenNumber; i++){
            TokenView tokenView = new TokenView();
            this.getChildren().add(tokenView);
        }
        label.setText(Integer.toString(tokenNumber));
    }

    public void setTokenNumber(){
        if(tokenNumber == 0)
            tokenNumber ++;
        else tokenNumber +=2;
    }

    public int getTokenNumber(){
        return tokenNumber;
    }

}
