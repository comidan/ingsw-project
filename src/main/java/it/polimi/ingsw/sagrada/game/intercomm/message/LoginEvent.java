package it.polimi.ingsw.sagrada.game.intercomm.message;

import it.polimi.ingsw.sagrada.game.intercomm.Message;

public class LoginEvent implements Message {
    private String username;
    private String password;

    public LoginEvent(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }
}
