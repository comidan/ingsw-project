/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.ingsw.sagrada.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 *
 * @author Daniele
 */
public class DiceView extends ImageView{

    private static final String DICE_IMAGE_ROOT_PATH = "src/main/resources/images/DiceImages/";
    private ClickHandler clickHandler;
    private Constraint color;
    private Constraint value;
    private int id;

    public DiceView(Constraint color, Constraint value, int id, ClickedObject clickedObject) {
        this.color = color;
        this.value = value;
        this.id = id;
        setImage(new Image(new File(DICE_IMAGE_ROOT_PATH + Constraint.getDiceFileName(color, value)).toURI().toString(), 50, 50, true, false));
        this.clickHandler = ClickHandler.getDiceButtonController(clickedObject);
    }

    public Constraint getColor() {
        return color;
    }

    public Constraint getValue() {
        return value;
    }

    public int getDiceID() {
        return id;
    }

    public ClickHandler getClickHandler() {
        return clickHandler;
    }
}