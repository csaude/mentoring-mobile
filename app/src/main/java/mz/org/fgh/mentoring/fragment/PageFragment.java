package mz.org.fgh.mentoring.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.model.QuestionAnswer;

public class PageFragment extends BaseFragment {

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

    private MentoringActivity activity;
    private Bundle activityBundle;
    private Question question;

    public PageFragment() {
    }


    @Override
    public void onCreateView() {

        activity = (MentoringActivity) getActivity();
        activityBundle = activity.getBundle();

        final Bundle bundle = getArguments();
        question = (Question) bundle.get(SwipeAdapter.QUESTION);

        boolean isLastPage = bundle.getBoolean(SwipeAdapter.LAST_PAGE);
        textView.setText(question.getQuestion());

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
        activity.submitProcess();
    }

    @OnClick(R.id.fragment_page_competent)
    public void onClickCompentent(View view) {

        if (((RadioButton) view).isChecked()) {
            activityBundle.putString(question.getUuid(), QuestionAnswer.COMPETENT.getValue());
        }
    }

    @OnClick(R.id.fragment_page_unsatisfactory)
    public void onClickUnSatosfactory(View view) {

        if (((RadioButton) view).isChecked()) {
            activityBundle.putString(question.getUuid(), QuestionAnswer.UNSATISFATORY.getValue());
        }
    }

    @OnClick(R.id.fragment_page_non_applicable)
    public void onClickNonApplicable(View view) {

        if (((RadioButton) view).isChecked()) {
            activityBundle.putString(question.getUuid(), QuestionAnswer.NON_APPICABLE.getValue());
        }
    }
}
