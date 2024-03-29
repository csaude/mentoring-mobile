package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.QuestionAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.QuestionCategory;
import mz.org.fgh.mentoring.event.ErrorEvent;
import mz.org.fgh.mentoring.util.Validations;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class QuestionFragment extends BaseFragment implements FragmentValidator {

    @BindView(R.id.questions)
    ListView questionsList;

    @BindView(R.id.iterations)
    TextView iterations;

    @BindView(R.id.fragment_questions_iteration_type)
    ImageView iterationType;

    @Inject
    EventBus eventBus;

    private List<FormQuestion> formQuestions;

    private boolean valid = false;

    /**
     * Declare form globally to use on validation
     */
    private Form form;

    @Override
    public int getResourceId() {
        return R.layout.fragment_questions;
    }

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        Bundle bundle = getArguments();
        form = (Form) bundle.get("form");
        String questionCategory = bundle.getString("category");

        formQuestions = form.getFormQuestionsByCategory(questionCategory);

        QuestionAdapter adapter = new QuestionAdapter(getActivity(), formQuestions);
        adapter.setComponent(component);
        questionsList.setAdapter(adapter);

        iterations.setText(bundle.getString("target"));
        iterationType.setImageDrawable(ContextCompat.getDrawable(getActivity(), bundle.getInt("iterationTypeImg")));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        for (FormQuestion formQuestion : formQuestions) {

            if (formQuestion.getAnswer() == null) {
                viewPager.setCurrentItem(position);
                eventBus.post(new ErrorEvent(getString(R.string.all_questions_must_be_answered)));
                return;
            }
        }

        if(form.getUuid().equals("122399f86199439cbbfe3deef149be87")){

            Validations v =Validations.getInstance();

            if(v.getQuestion2()>v.getQuestion1()){

                viewPager.setCurrentItem(position);
                eventBus.post(new ErrorEvent(getString(R.string.attended_must_be_greather_than_previous)));
                return;

            }
            else

            if((v.getQuestion3()+v.getQuestion2())>v.getQuestion1()){

                viewPager.setCurrentItem(position);
                eventBus.post(new ErrorEvent(getString(R.string.previous_must_be_greather_than_previous_and_tested)));
                return;

            } else
            if(v.getQuestion4()>v.getQuestion3()){

                viewPager.setCurrentItem(position);
                eventBus.post(new ErrorEvent(getString(R.string.tested_must_be_greather_than_positive)));
                return;

            }

            if(v.getQuestion5()>v.getQuestion4()){

                viewPager.setCurrentItem(position);
                eventBus.post(new ErrorEvent(getString(R.string.positive_must_be_greather_than_enrolled)));
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
