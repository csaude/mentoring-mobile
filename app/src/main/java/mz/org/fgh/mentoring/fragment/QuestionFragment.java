package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.QuestionAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.QuestionCategory;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.validator.FragmentValidator;
import mz.org.fgh.mentoring.validator.IterationFragment;

public class QuestionFragment extends BaseFragment implements FragmentValidator {

    @BindView(R.id.questions)
    ListView questionsList;

    @BindView(R.id.iterations)
    TextView iterations;

    private List<FormQuestion> formQuestions;

    private boolean valid = false;

    @Override
    public int getResourceId() {
        return R.layout.fragment_questions;
    }

    @Override
    public void onCreateView() {

        Bundle bundle = getArguments();
        Form form = (Form) bundle.get("form");
        QuestionCategory questionCategory = (QuestionCategory) bundle.get("category");


        MentoringComponent component = ((MentoringApplication) getActivity().getApplication()).getMentoringComponent();

        formQuestions = form.getFormQuestionsByCategory(questionCategory);

        QuestionAdapter adapter = new QuestionAdapter(getActivity(), formQuestions);
        adapter.setComponent(component);
        questionsList.setAdapter(adapter);

        iterations.setText(bundle.getString("target"));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        for (FormQuestion formQuestion : formQuestions) {

            if (formQuestion.getAnswer() == null) {
                Snackbar.make(getView(), getString(R.string.all_questions_must_be_answered), Snackbar.LENGTH_SHORT).show();
                viewPager.setCurrentItem(position);
                return;
            }
        }

        this.valid = true;
    }

    @Override
    public boolean isValid() {
        return valid;
    }
}
