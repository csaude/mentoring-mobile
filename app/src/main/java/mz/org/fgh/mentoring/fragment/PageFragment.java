package mz.org.fgh.mentoring.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.AnswerActivity;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.model.QuestionAnswer;
import mz.org.fgh.mentoring.util.AnswerUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class PageFragment extends BaseFragment implements FragmentValidator {

    @BindView(R.id.text_view)
    TextView textView;

    @BindView(R.id.save_btn)
    Button saveBtn;

    @BindView(R.id.fragment_page_competent)
    RadioButton competentRd;

    @BindView(R.id.fragment_page_unsatisfactory)
    RadioButton unsatisfactoryRd;

    @BindView(R.id.fragment_page_non_applicable)
    RadioButton nonApplicabletRd;

    @Inject
    EventBus eventBus;

    private Question question;

    private Answer answer;

    private AnswerActivity activity;

    public PageFragment() {
    }


    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        final Bundle bundle = getArguments();
        question = (Question) bundle.get(SwipeAdapter.QUESTION);

        boolean isLastPage = bundle.getBoolean(SwipeAdapter.LAST_PAGE);
        textView.setText(question.getQuestion());

        activity = (AnswerActivity) getActivity();

        if (!isLastPage) {
            saveBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getResourceId() {
        return R.layout.page_frgament;
    }

    @OnClick(R.id.save_btn)
    public void onClickSaveBtn() {

        if (!AnswerUtil.wasQuestionAnswered(activity.getAnswers(), question)) {
            Snackbar.make(getView(), getString(R.string.none_answer_was_seleted), Snackbar.LENGTH_SHORT).show();
            return;
        }

        eventBus.post(new ProcessEvent());
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
        answer = question.getQuestionType().getAnswer();
        answer.setQuestion(question);
        answer.setValue(value);

        eventBus.post(new AnswerEvent(this.answer));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (question == null || AnswerUtil.wasQuestionAnswered(activity.getAnswers(), question)) {
            return;
        }

        viewPager.setCurrentItem(position);
        Snackbar.make(getView(), getString(R.string.none_answer_was_seleted), Snackbar.LENGTH_SHORT).show();
    }
}
