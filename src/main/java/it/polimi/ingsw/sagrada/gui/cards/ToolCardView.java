package it.polimi.ingsw.sagrada.gui.cards;

import it.polimi.ingsw.sagrada.gui.components.TokenView;
import it.polimi.ingsw.sagrada.gui.utils.GUIManager;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


/**
 * The Class ToolCardView.
 */
public class ToolCardView extends StackPane {

    /** The Constant TOOL_IMAGE_ROOT_PATH. */
    private static final String TOOL_IMAGE_ROOT_PATH = "/images/ToolImages/";
    
    /** The id. */
    private int id;


    /** The label. */
    private Label label;

    /** The number of tokens currently on the card. */
    private int currentTokenNumber;

    /**
     * Instantiates a new tool card view.
     *
     * @param id the toolcard id
     */
    ToolCardView(int id) {
        this.id = id;
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(ToolCardView.class.getResourceAsStream(TOOL_IMAGE_ROOT_PATH + id + ".jpg"), GUIManager.getGameWidthPixel(19), GUIManager.getGameHeightPixel(25), true, false));
        this.getChildren().add(imageView);
        label = new Label();
        this.getChildren().add(label);

    }

    /**
     * Sets the tool click handler to handle the possibility to buy the toolcard.
     *
     * @param toolClickHandler the new tool click handler
     */
    public void setToolClickHandler(EventHandler<MouseEvent> toolClickHandler){
        setOnMouseClicked(toolClickHandler);
    }

    /**
     * Gets the tool id.
     *
     * @return the toolcard id
     */
    public int getToolId(){
        return id;
    }

    /**
     * Adds the token on the toolcard when it's used.
     *
     * @param tokenNumber the token number
     */
    public void addToken(int tokenNumber){
        for(int i = 0; i<tokenNumber; i++){
            TokenView tokenView = new TokenView();
            this.getChildren().add(tokenView);
        }
        currentTokenNumber += tokenNumber;
        label.setText(Integer.toString(currentTokenNumber));
    }



}
