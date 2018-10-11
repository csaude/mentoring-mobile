package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.event.EventType;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class TextFragment extends BaseFragment implements FragmentValidator {

    @BindView(R.id.text_view)
    TextView textView;

    @BindView(R.id.text_value)
    EditText textValue;

    @BindView(R.id.save_btn)
    Button saveBtn;

    @Inject
    EventBus eventBus;

    private Question question;

    @Override
    public int getResourceId() {
        return R.layout.fragment_text;
    }

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        final Bundle bundle = getArguments();
        question = (Question) bundle.get(SwipeAdapter.QUESTION);

        boolean isLastPage = bundle.getBoolean(SwipeAdapter.LAST_PAGE);
        textView.setText(question.getQuestion());

        if (!isLastPage) {
            saveBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (textValue == null) {
            return;
        }

        if (!isEmpty()) {

            getAnswer(textValue.getText().toString());

            return;
        }

        viewPager.setCurrentItem(position);
        Snackbar.make(textView, getString(R.string.the_value_is_empty), Snackbar.LENGTH_SHORT).show();
    }

    private boolean isEmpty() {
        return textValue.getText().toString().trim().isEmpty();
    }

    @Override
    public boolean isValid() {
        return !isEmpty();
    }

    private void getAnswer(String value) {
        Answer answer = question.getQuestionType().getAnswer();
        answer.setValue(value);
        answer.setQuestion(question);

        eventBus.post(new AnswerEvent(answer));
    }

    @OnClick(R.id.save_btn)
    public void onClickSave() {

        if (isEmpty()) {
            Snackbar.make(textView, getString(R.string.the_value_is_empty), Snackbar.LENGTH_SHORT).show();
            return;
        }

        getAnswer(textValue.getText().toString());
        eventBus.post(new ProcessEvent(EventType.SUBMIT));
    }
}
