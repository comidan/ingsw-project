package it.polimi.ingsw.sagrada.network.utilities;


public class LoginManager {

    private static LoginManager loginManager;
    private KeyParser keyParser;


    private LoginManager() {
        keyParser = new KeyParser();

    }

    public static LoginManager getLoginManager() {
        if (loginManager == null)
            loginManager = new LoginManager();
        return loginManager;
    }


    public boolean checkLogin() {


        //search in database
        return false;
    }

    //ascolta pacchetti di login dal client
    //comunica col db, fa query e vede corrispondenza
    //ritorna evento per comunicare esito
    // oppure potrebbe essere future invece che evento
    //comunica esito a server, se positivo server passa clientsocket a match lobby
}
