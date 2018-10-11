package mz.org.fgh.mentoring.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.provider.AnswerProvider;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.event.EventType;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.model.QuestionAnswer;
import mz.org.fgh.mentoring.provider.SessionProvider;
import mz.org.fgh.mentoring.util.AnswerUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;
import mz.org.fgh.mentoring.validator.IterationFragment;

public class PageFragment extends BaseFragment implements FragmentValidator, IterationFragment {

    @BindView(R.id.text_view)
    TextView textView;

    @BindView(R.id.save_btn)
    Button saveBtn;

    @BindView(R.id.terminate_btn)
    Button terminateBtn;

    @BindView(R.id.continue_btn)
    Button continueBtn;

    @BindView(R.id.fragment_page_competent)
    RadioButton competentRd;

    @BindView(R.id.fragment_page_unsatisfactory)
    RadioButton unsatisfactoryRd;

    @BindView(R.id.fragment_page_non_applicable)
    RadioButton nonApplicabletRd;

    @BindView(R.id.reason)
    EditText reason;

    @BindView(R.id.iterations)
    TextView iteractions;

    @BindView(R.id.terminate_reason)
    TextView terminateReason;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @Inject
    EventBus eventBus;

    private Question question;

    private AnswerProvider activity;

    private Bundle bundle;

    private SessionProvider sessionProvider;

    public PageFragment() {
    }

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        bundle = getArguments();
        question = (Question) bundle.get(SwipeAdapter.QUESTION);

        boolean isLastPage = bundle.getBoolean(SwipeAdapter.LAST_PAGE);
        textView.setText(question.getQuestion());

        activity = (AnswerProvider) getActivity();

        if (getActivity() instanceof SessionProvider) {
            sessionProvider = (SessionProvider) getActivity();
        }

        updateIterations();
    }

    @Override
    public void updateIterations() {
        if (activity == null || iteractions == null) {
            return;
        }

        if (sessionProvider == null) {
            return;
        }

        iteractions.setText(getString(R.string.iterations) + ":" + (sessionProvider.getSession().getMentorships().size() + 1) + "/" + sessionProvider.getSession().getForm().getTargetPatient());
    }

    @Override
    public int getResourceId() {
        return R.layout.page_frgament;
    }

    @OnClick(R.id.save_btn)
    public void onClickSaveBtn() {

        if (!wasQuestionAnswered()) {
            Snackbar.make(getView(), getString(R.string.none_answer_was_seleted), Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (reason.getVisibility() == View.VISIBLE && reason.getText().toString().trim().isEmpty()) {
            Snackbar.make(getView(), getString(R.string.reason_must_be_informed), Snackbar.LENGTH_SHORT).show();
            return;
        }

        ProcessEvent processEvent = new ProcessEvent(EventType.SUBMIT);
        processEvent.setReason(reason.getText().toString().isEmpty() ? null : reason.getText().toString());

        eventBus.post(processEvent);
    }

    @OnClick(R.id.continue_btn)
    public void onClickContinueBtn() {

        if (!wasQuestionAnswered()) {
            Snackbar.make(getView(), getString(R.string.none_answer_was_seleted), Snackbar.LENGTH_SHORT).show();
            return;
        }

        eventBus.post(new ProcessEvent(EventType.CONTINUE));
    }

    @OnClick(R.id.fragment_page_competent)
    public void onClickCompentent(View view) {

        if (((RadioButton) view).isChecked()) {
            getAnswer(QuestionAnswer.COMPETENT.getValue());
        }
    }

    @OnClick(R.id.fragment_page_unsatisfactory)
    public void onClickUnSatosfactory(View view) {

        if (((RadioButton) view).isChecked()) {
            getAnswer(QuestionAnswer.UNSATISFATORY.getValue());
        }
    }

    @OnClick(R.id.fragment_page_non_applicable)
    public void onClickNonApplicable(View view) {

        if (((RadioButton) view).isChecked()) {
            getAnswer(QuestionAnswer.NON_APPICABLE.getValue());
        }
    }

    private void getAnswer(String value) {
        Answer answer = question.getQuestionType().getAnswer();
        answer.setQuestion(question);
        answer.setValue(value);

        eventBus.post(new AnswerEvent(answer));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (competentRd == null || unsatisfactoryRd == null || nonApplicabletRd == null) {
            return;
        }

        if (wasQuestionAnswered()) {
            return;
        }

        viewPager.setCurrentItem(position);
        Snackbar.make(getView(), getString(R.string.none_answer_was_seleted), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean isValid() {
        return wasQuestionAnswered();
    }

    @OnClick(R.id.terminate_btn)
    public void onClickTerminate() {
        saveBtn.setVisibility(View.VISIBLE);
        terminateReason.setVisibility(View.VISIBLE);
        reason.setVisibility(View.VISIBLE);

        terminateBtn.setVisibility(View.INVISIBLE);
        continueBtn.setVisibility(View.INVISIBLE);
    }

    private boolean wasQuestionAnswered() {
        return competentRd.isChecked() || unsatisfactoryRd.isChecked() || nonApplicabletRd.isChecked();
    }
}
