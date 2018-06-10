package it.polimi.ingsw.sagrada.gui.utils;

import java.awt.*;

public class Resizer {

    private double windowHeight;
    private double windowWidth;

    public Resizer() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.windowWidth = gd.getDisplayMode().getWidth();
        this.windowHeight = gd.getDisplayMode().getHeight();
    }

    public double getHeightPixel(int perc) {
        return (perc * windowHeight / 100);
    }

    public double getWidthPixel(int perc) {
        return (perc * windowWidth / 100);
    }

    public double getWindowHeight(){
        return this.windowHeight;
    }

    public double getWindowWidth(){
        return this.windowWidth;
    }

}
