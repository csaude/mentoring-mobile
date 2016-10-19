package mz.org.fgh.mentoring.infra;

import android.content.Context;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public class Auth {

    private Context context;
    private User user;

    public Auth(Context context) {
        this.context = context;
        this.user = new User();
    }

    public User getUser() {
        return user;
    }
}
