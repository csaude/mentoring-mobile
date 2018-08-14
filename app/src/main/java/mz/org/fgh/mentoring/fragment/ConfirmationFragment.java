package mz.org.fgh.mentoring.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.event.EventType;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.util.KeyboardUtil;

public class ConfirmationFragment extends BaseFragment {

    @BindView(R.id.text_view)
    TextView textView;

    @BindView(R.id.terminate_reason)
    TextView terminateReason;

    @BindView(R.id.reason)
    EditText reasonTxt;

    @BindView(R.id.terminate_btn)
    Button terminateBtn;

    @BindView(R.id.terminate_target)
    TextView terminateTarget;

    @BindView(R.id.continue_yes)
    Button continueYes;

    @BindView(R.id.continue_no)
    Button continueNo;

    @Inject
    EventBus eventBus;

    private Session session;

    @Override
    public int getResourceId() {
        return R.layout.confirmation_frgament;
    }

    @Override
    public void onCreateView() {
        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        reasonTxt.setVisibility(View.GONE);
        terminateReason.setVisibility(View.GONE);
        terminateBtn.setVisibility(View.INVISIBLE);

        Bundle bundle = getArguments();
        this.session = (Session) bundle.get("session");
        String target = bundle.getString("target");

        if (session.getReason() != null) {
            reasonTxt.setVisibility(View.VISIBLE);
            terminateReason.setVisibility(View.VISIBLE);
            terminateBtn.setVisibility(View.VISIBLE);

            terminateTarget.setVisibility(View.GONE);
            continueYes.setVisibility(View.GONE);
            continueNo.setVisibility(View.GONE);
        }

        terminateTarget.setText(target.replace("/", " " + getString(R.string.of) + " ") + " " + getString(R.string.iteration_times));

        if (session.isComplete()) {
            terminateTarget.setVisibility(View.GONE);
            continueYes.setVisibility(View.GONE);
            continueNo.setVisibility(View.GONE);

            terminateBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.continue_no)
    public void onClickContinueNo() {
        terminateTarget.setVisibility(View.GONE);
        continueYes.setVisibility(View.GONE);
        continueNo.setVisibility(View.GONE);

        reasonTxt.setVisibility(View.VISIBLE);
        terminateReason.setVisibility(View.VISIBLE);

        terminateBtn.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.terminate_btn)
    public void onClickTerminateBtn() {

        ProcessEvent event = new ProcessEvent(EventType.TERMINATE);

        if (session.isComplete()) {
            eventBus.post(event);
            return;
        }

        if (reasonTxt.getText().length() == 0) {
            reasonTxt.setError(getString(R.string.required_field));
            return;
        }

        KeyboardUtil.hideKyBoard(getActivity(), terminateBtn);

        event.setReason(reasonTxt.getText().toString());

        eventBus.post(event);
    }

    @OnClick(R.id.continue_yes)
    public void onClickContinueBtn() {
        eventBus.post(new ProcessEvent(EventType.CONTINUE));
    }
}
