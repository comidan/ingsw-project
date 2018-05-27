package it.polimi.ingsw.sagrada.gui;

//TO BE CONNECTED TO MODEL


public interface WindowModelInterface {


    // public String getWindowPath();

    public default String getUrl(int id) {

        String url;
        switch (id) {
            case 1:
                url = "images/kaleidoscopic.jpg";
                break;
            case 2:
                url = "images/firmitas.jpg";
                break;
            case 3:
                url = "images/fractaldrops.jpg";
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
