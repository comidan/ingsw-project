package it.polimi.ingsw.sagrada.gui.test;


//TO BE CONNECTED TO MODEL


/**
 * The Interface WindowModelInterface.
 */
public interface WindowModelInterface {


    // public String getWindowPath();

    /**
     * Gets the url.
     *
     * @param id the id
     * @return the url
     */
    public default String getUrl(int id) {

        String url;
        switch (id) {
            case 1:
                url = "images/window_images/window0Front.jpg";
                break;
            case 2:
                url = "images/window_images/window0Rear.jpg";
                break;
            case 3:
                url = "images/window_images/window1Front.jpg";
                break;
            case 4:
                url = "images/ripple.jpg";
                break;
            default:
                url = null;
        }
        return url;
    }
}
