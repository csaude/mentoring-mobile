package mz.org.fgh.mentoring.infra;

import android.app.Application;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public class MentoringApplication extends Application {

    private Auth auth;

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new Auth(this);
    }

    public Auth getAuth() {
        return auth;
    }
}
