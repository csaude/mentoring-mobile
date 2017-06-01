package mz.org.fgh.mentoring.model;

import mz.org.fgh.mentoring.infra.UserContext;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public abstract class BeanResource {

    private UserContext userContext;

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    public UserContext getUserContext() {
        return userContext;
    }
}
