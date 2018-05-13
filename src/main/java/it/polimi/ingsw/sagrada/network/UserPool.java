package it.polimi.ingsw.sagrada.network;

import java.util.ArrayList;
import java.util.List;

public class UserPool {

    private List<String> userNames;

    public void UserPool() {
        userNames = new ArrayList<>();
    }


    public boolean addUser(String userName) {

        if (userNames.stream().filter(name -> name.equals(userName)).count() > 0)
            return false;
        else {
            this.userNames.add(userName);
            return true;
        }

    }


}
