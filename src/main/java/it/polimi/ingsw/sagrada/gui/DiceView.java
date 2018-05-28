/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.ingsw.sagrada.gui;

import javafx.scene.image.ImageView;

import java.awt.*;
import javafx.event.EventHandler;

/**
 *
 * @author Daniele
 */
public class DiceView extends ImageView{
    

    private Constraint color;
    private Constraint value;
    private int id;

    public DiceView(Constraint color, Constraint value, int id) {
        this.color = color;
        this.id = id;
    }

    public int getDiceID() {
        return id;
    }
}