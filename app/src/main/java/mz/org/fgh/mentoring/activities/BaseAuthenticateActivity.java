package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;

import mz.org.fgh.mentoring.infra.UserContext;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public abstract class BaseAuthenticateActivity extends BaseActivity {
    public static BaseAuthenticateActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserContext user = application.getAuth().getUser();

        if (!user.isLogged()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        onMentoringCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BaseAuthenticateActivity.instance = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseAuthenticateActivity.instance = null;
    }

    protected abstract void onMentoringCreate(Bundle bundle);
}
