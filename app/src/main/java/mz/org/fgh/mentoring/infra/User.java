package mz.org.fgh.mentoring.infra;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public class User extends GenericEntity {

    private String username;

    private String password;

    private boolean isLogged;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }
}
