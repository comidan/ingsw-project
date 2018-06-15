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


/**
 * The Class ToolCardView.
 */
public class ToolCardView extends StackPane {

    /** The Constant TOOL_IMAGE_ROOT_PATH. */
    private static final String TOOL_IMAGE_ROOT_PATH = "/images/ToolImages/";
    
    /** The id. */
    private int id;
    
    /** The resizer. */
    private Resizer resizer;
    
    /** The token number. */
    private int tokenNumber = 0;
    
    /** The token grid. */
    private GridPane tokenGrid;
    
    /** The label. */
    private Label label;

    /**
     * Instantiates a new tool card view.
     *
     * @param id the id
     */
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

    /**
     * Sets the tool click handler.
     *
     * @param toolClickHandler the new tool click handler
     */
    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        this.setOnMouseClicked(toolClickHandler);
    }

    /**
     * Gets the tool id.
     *
     * @return the tool id
     */
    public int getToolId(){
        return id;
    }

    /**
     * Adds the token.
     */
    public void addToken(){
        setTokenNumber();
        for(int i = 0; i<tokenNumber; i++){
            TokenView tokenView = new TokenView();
            this.getChildren().add(tokenView);
        }
        label.setText(Integer.toString(tokenNumber));
    }

    /**
     * Sets the token number.
     */
    public void setTokenNumber(){
        if(tokenNumber == 0)
            tokenNumber ++;
        else tokenNumber +=2;
    }

    /**
     * Gets the token number.
     *
     * @return the token number
     */
    public int getTokenNumber(){
        return tokenNumber;
    }

}
