package mz.org.fgh.mentoring.activities;

import android.arch.lifecycle.ProcessLifecycleOwner;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import mz.org.fgh.mentoring.InputMethodManagerLeaks;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.infra.MentoringApplication;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected MentoringApplication application;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (MentoringApplication) getApplication();
        InputMethodManagerLeaks.fixFocusedViewLeak(application);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(application);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
}
