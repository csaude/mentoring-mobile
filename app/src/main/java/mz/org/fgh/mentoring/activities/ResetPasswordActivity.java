package mz.org.fgh.mentoring.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.process.model.MentorshipBeanResource;
import mz.org.fgh.mentoring.service.UserService;
import mz.org.fgh.mentoring.util.KeyboardUtil;
import retrofit2.Call;

public class ResetPasswordActivity extends BaseActivity {

    @BindView(R.id.reset_email)
    EditText resetEmail;

    @Inject
    UserService userService;

    @Inject
    EventBus eventBus;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        eventBus.register(this);
    }

    @OnClick(R.id.reset_send)
    public void onSend() {

        if (resetEmail.getText().length() == 0) {
            resetEmail.setError(getString(R.string.required_field));
            return;
        }

        KeyboardUtil.hideKyBoard(this, resetEmail);

        UserContext context = new UserContext();
        context.setEmail(resetEmail.getText().toString());

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.wait));
        progressDialog.setMessage(getString(R.string.processing));
        progressDialog.show();

        userService.resetPassword(context);
    }

    @Subscribe
    public void onResetEvent(MessageEvent event) {

        progressDialog.dismiss();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.reset_password))
                .setIcon(R.mipmap.ic_info)
                .setCancelable(false);

        if (event.getError() != null) {
            alertDialog.setMessage(getString(R.string.reset_password_error)).
                    setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
            return;
        }

        alertDialog.setMessage(getString(R.string.reset_password_success)).
                setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }
}

