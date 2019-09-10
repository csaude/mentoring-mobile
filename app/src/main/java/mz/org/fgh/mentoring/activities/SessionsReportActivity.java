package mz.org.fgh.mentoring.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.service.LoadMetadataService;
import retrofit2.Retrofit;

public class SessionsReportActivity extends BaseAuthenticateActivity implements View.OnClickListener {
    @Inject
    @Named("mentoring")
    Retrofit retrofit;

    @Inject
    LoadMetadataService loadMetadataService;

    @BindView(R.id.mUserNameTextView)
    TextView mUsernameTextView;

    ProgressDialog progressDialog;

    @Override
    protected void onMentoringCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sessions_report);
        progressDialog = new ProgressDialog(this);
        application.getMentoringComponent().inject(this);
        mUsernameTextView.setText(application.getAuth().getUser().getFullName());
        loadMetadataService.load(SessionsReportActivity.this, progressDialog,
                application.getAuth().getUser());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(getClass().getSimpleName(), "In on touch event");
        startMainActivity();
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        Log.d(getClass().getSimpleName(), "In on click event");
        startMainActivity();
    }

    private void startMainActivity() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivityIntent);
    }
}
