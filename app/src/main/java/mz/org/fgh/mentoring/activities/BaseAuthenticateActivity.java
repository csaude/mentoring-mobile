package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;

import mz.org.fgh.mentoring.infra.UserContext;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public abstract class BaseAuthenticateActivity extends BaseActivity {

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

    protected abstract void onMentoringCreate(Bundle bundle);
}
