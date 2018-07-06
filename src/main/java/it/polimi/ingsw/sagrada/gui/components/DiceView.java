/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.ingsw.sagrada.gui.components;

import it.polimi.ingsw.sagrada.gui.utils.Constraint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * The Class DiceView.
 *
 * @author Daniele
 */
public class DiceView extends ImageView{

    /** The Constant DICE_IMAGE_ROOT_PATH. */
    private static final String DICE_IMAGE_ROOT_PATH = "/images/DiceImages/";
    
    /** The color. */
    private Constraint color;
    
    /** The value. */
    private Constraint value;
    
    /** The id. */
    private int id;

    private int roundNumber;

    /**
     * Instantiates a new dice view.
     *
     * @param color the color
     * @param value the value
     * @param id the id
     */
    public DiceView(Constraint color, Constraint value, int id) {
        this.color = color;
        this.value = value;
        this.id = id;
        setImage(new Image(DiceView.class.getResourceAsStream(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(color, value)), 50, 50, true, false));
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public Constraint getColor() {
        return color;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Constraint getValue() {
        return value;
    }

    /**
     * Gets the dice ID.
     *
     * @return the dice ID
     */
    public int getDiceID() {
        return id;
    }

    /**
     * Gets the round number associated to the dice (if present).
     *
     * @return the round number
     */
    public int getRoundNumber(){
        return roundNumber;
    }

    /**
     * Sets the round number associated to the dice (if present).
     *
     * @param number the round number
     */
    public void setRoundNumber(int number){
        this.roundNumber = number;
    }


}