package mz.org.fgh.mentoring.event;

import mz.org.fgh.mentoring.infra.UserContext;

/**
 * Created by mhawilamhawila on 12/27/18.
 */

public class LoginEvent {
    private UserContext userContext;

    public LoginEvent() {
    }

    public LoginEvent(UserContext userContext) {
        this.userContext = userContext;
    }

    public UserContext getUserContext() {
        return userContext;
    }

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }
}
