package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public abstract class BaseAuthenticateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!application.getAuth().getUser().isLogged()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        onMentoringCreate(savedInstanceState);
    }

    protected abstract void onMentoringCreate(Bundle bundle);
}
