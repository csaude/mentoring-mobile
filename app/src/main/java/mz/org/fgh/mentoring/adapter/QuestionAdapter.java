package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.QuestionType;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.model.QuestionAnswer;

/**
 * Created by steliomo on 4/18/18.
 */

public class QuestionAdapter extends BaseAbstractAdapter {

    @BindView(R.id.adapter_root)
    LinearLayout linearLayout;

    @BindView(R.id.adapter_question)
    TextView tvQuestion;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @Nullable
    @BindView(R.id.adapter_competent)
    RadioButton competentRd;

    @Nullable
    @BindView(R.id.adapter_unsatisfactory)
    RadioButton unsastifactoryRd;

    @Nullable
    @BindView(R.id.adapter_yes)
    RadioButton yesRd;

    @Nullable
    @BindView(R.id.adapter_no)
    RadioButton noRd;

    @Nullable
    @BindView(R.id.adapter_not_applicable)
    RadioButton notApplicableRd;

    @Inject
    EventBus eventBus;

    private MentoringComponent component;

    private Context context;

    private List<FormQuestion> formQuestions;

    public QuestionAdapter(Context context, List<FormQuestion> formQuestions) {
        this.context = context;
        this.formQuestions = formQuestions;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public int getResourceId(int position) {

        FormQuestion formQuestion = formQuestions.get(position);

        switch (formQuestion.getQuestion().getQuestionType()) {
            case TEXT:
                return R.layout.text_adapter;

            case BOOLEAN:
                return R.layout.boolean_adapter;
        }

        return R.layout.text_adapter;
    }

    @Override
    public void onCreateView(int position) {
        this.component.inject(this);
        FormQuestion formQuestion = formQuestions.get(position);
        linearLayout.setTag(formQuestion);
        tvQuestion.setText(formQuestion.getQuestion().getQuestion());

        markAnwser(formQuestion.getAnswer());

        if (formQuestion.getApplicable() == Boolean.TRUE) {
            notApplicableRd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getCount() {
        return formQuestions.size();
    }

    @Override
    public FormQuestion getItem(int position) {
        return formQuestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return formQuestions.get(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return QuestionType.values().length;
    }

    @Optional
    @OnClick(R.id.adapter_unsatisfactory)
    public void onClickUnsastifactory(View view) {
        if (((RadioButton) view).isChecked()) {
            response(getFormQuestion(view), QuestionAnswer.UNSATISFATORY.getValue());
        }
    }

    @Optional
    @OnClick(R.id.adapter_competent)
    public void onClickCompetent(View view) {
        if (((RadioButton) view).isChecked()) {
            response(getFormQuestion(view), QuestionAnswer.COMPETENT.getValue());
        }
    }

    @Optional
    @OnClick(R.id.adapter_yes)
    public void onClickYes(View view) {
        if (((RadioButton) view).isChecked()) {
            response(getFormQuestion(view), Boolean.TRUE.toString());
        }
    }

    @Optional
    @OnClick(R.id.adapter_no)
    public void onClickNo(View view) {
        if (((RadioButton) view).isChecked()) {
            response(getFormQuestion(view), Boolean.FALSE.toString());
        }
    }

    @Optional
    @OnClick(R.id.adapter_not_applicable)
    public void onClickNotApplicable(View view) {
        if (((RadioButton) view).isChecked()) {
            response(getFormQuestion(view), QuestionAnswer.NON_APPICABLE.getValue());
        }
    }

    private FormQuestion getFormQuestion(View view) {
        LinearLayout linearLayout = (LinearLayout) view.getParent().getParent();
        return (FormQuestion) linearLayout.getTag();
    }

    @NonNull
    private void response(FormQuestion formQuestion, String value) {
        Answer answer = formQuestion.getQuestion().getQuestionType().getAnswer();
        answer.setQuestion(formQuestion.getQuestion());
        answer.setValue(value);

        formQuestion.setAnswer(answer);

        eventBus.post(new AnswerEvent(answer));
    }

    public void setComponent(MentoringComponent component) {
        this.component = component;
    }

    public void markAnwser(Answer answer) {
        radioGroup.clearCheck();

        if (answer == null) {
            return;
        }

        switch (answer.getValue()) {
            case "COMPETENTE":
                competentRd.setChecked(Boolean.TRUE);
                break;

            case "NAO SATISFATRIO":
                unsastifactoryRd.setChecked(Boolean.TRUE);
                break;

            case "NA":
                notApplicableRd.setChecked(Boolean.TRUE);
                break;

            case "true":
                yesRd.setChecked(Boolean.TRUE);
                break;

            case "false":
                noRd.setChecked(Boolean.TRUE);
                break;
        }
    }
}
