package it.polimi.ingsw.sagrada.gui.utils;

import java.awt.*;


/**
 * The Class Resizer.
 */
public class Resizer {

    /** The window height. */
    private double windowHeight;
    
    /** The window width. */
    private double windowWidth;

    /**
     * Instantiates a new resizer.
     */
    public Resizer() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.windowWidth = gd.getDisplayMode().getWidth();
        this.windowHeight = gd.getDisplayMode().getHeight();
    }

    /**
     * Gets the height pixel.
     *
     * @param perc the perc
     * @return the height pixel
     */
    public double getHeightPixel(int perc) {
        return perc * windowHeight / 100;
    }

    /**
     * Gets the width pixel.
     *
     * @param perc the perc
     * @return the width pixel
     */
    public double getWidthPixel(int perc) {
        return perc * windowWidth / 100;
    }

    /**
     * Gets the window height.
     *
     * @return the window height
     */
    public double getWindowHeight(){
        return this.windowHeight;
    }

    /**
     * Gets the window width.
     *
     * @return the window width
     */
    public double getWindowWidth(){
        return this.windowWidth;
    }

}
