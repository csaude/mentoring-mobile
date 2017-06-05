package mz.org.fgh.mentoring.infra;

import android.content.Context;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public class Auth {

    private Context context;
    private UserContext user;

    public Auth(final Context context, final UserContext user) {
        this.context = context;
        this.user = user;
    }

    public UserContext getUser() {
        return user;
    }

    public void setUser(final UserContext user) {
        this.user = user;
    }
}
