package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.AlertListner;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.dialog.AlertDialogManager;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.service.UserService;

public class ChangePasswordActivity extends BaseAuthenticateActivity {

    @BindView(R.id.logged_user)
    TextView loggedUser;

    @BindView(R.id.new_password)
    EditText newPassword;

    @BindView(R.id.confirm_password)
    EditText confirmPassword;

    @BindView(R.id.change_password)
    Button changePassword;

    @Inject
    EventBus eventBus;

    @Inject
    UserService userService;

    private AlertDialogManager alertDialogManager;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_change_password);

        MentoringComponent mentoringComponent = application.getMentoringComponent();
        mentoringComponent.inject(this);

        loggedUser.setText(application.getAuth().getUser().getFullName());

        eventBus.register(this);
        alertDialogManager = new AlertDialogManager(this);
    }

    @OnClick(R.id.change_password)
    public void onClickChangePassword() {

        if (newPassword.getText().toString().isEmpty()) {
            newPassword.setError(getResources().getString(R.string.new_password_must_be_filled));
            return;
        }

        if (confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError(getResources().getString(R.string.confirm_password_must_be_filled));
            return;
        }

        if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.password_must_be_the_same), Toast.LENGTH_SHORT).show();
            return;
        }

        UserContext context = application.getAuth().getUser();
        context.setPassword(newPassword.getText().toString());

        userService.changeUserPassword(context);
    }

    @Subscribe
    public void onChangePassword(MessageEvent event) {

        if (event.getError() != null) {
            alertDialogManager.showAlert(getString(R.string.server_error_comunication));
            return;
        }

        alertDialogManager.showAlert(getString(R.string.password_changed_success), new AlertListner() {
            @Override
            public void perform() {
                startActivity(new Intent(ChangePasswordActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }
}
