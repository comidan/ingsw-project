package it.polimi.ingsw.sagrada.network.utilities;

public class LoginManager {

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
