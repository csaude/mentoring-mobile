package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Optional;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.QuestionType;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.model.QuestionAnswer;
import mz.org.fgh.mentoring.util.Validations;

/**
 * Created by steliomo on 4/18/18.
 */

public class QuestionAdapter extends BaseAbstractAdapter {

    @BindView(R.id.adapter_root)
    LinearLayout linearLayout;

    @BindView(R.id.adapter_question)
    TextView tvQuestion;

    /**
     * Added @Nullable annotation because this view is optional.
     * It is not necessary for NUMERIC Question Type.
     */
    @Nullable
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

    /**
     * This field comes from layout file 'numeric_adapter'.
     * Is necessary to handle NUMERIC questions.
     */
    @Nullable
    @BindView(R.id.numeric_value)
    EditText numericValue;

    @Inject
    EventBus eventBus;

    private MentoringComponent component;

    private Context context;

    private List<FormQuestion> formQuestions;

    /**
     * Declare formQuestion globally to use the variable on
     * onNumberChanged() method to get and populate  the answer
     * to the current NUMERIC question
     *
     */
    private FormQuestion formQuestion;

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

            /**
             * Support for NUMERIC Question Type
             */
            case NUMERIC:
                return R.layout.numeric_adapter;
        }

        return R.layout.text_adapter;
    }

    @Override
    public void onCreateView(int position) {
        this.component.inject(this);
        formQuestion = formQuestions.get(position);
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

    /**
     * Trigger event after Value Numeric Changed
     */
    @Optional
    @OnTextChanged(R.id.numeric_value)
    public void onNumberChanged() {
        if(numericValue.getText().toString().length()>0){
            response(formQuestion, numericValue.getText().toString());
            String questionCatergory=formQuestion.getQuestion().getQuestionsCategory().getCategory();
            Validations v = Validations.getInstance();
            if(questionCatergory.equals("Questão 1")){
                v.setQuestion1(Integer.valueOf(numericValue.getText().toString()));
                v.setQuestion2(0);
                v.setQuestion3(0);
                v.setQuestion4(0);
                v.setQuestion5(0);
            } else
            if(questionCatergory.equals("Questão 2")){
                v.setQuestion2(Integer.valueOf(numericValue.getText().toString()));
                v.setQuestion3(0);
                v.setQuestion4(0);
                v.setQuestion5(0);
            } else
            if(questionCatergory.equals("Questão 3")){
                v.setQuestion3(Integer.valueOf(numericValue.getText().toString()));
                v.setQuestion4(0);
                v.setQuestion5(0);
            } else
            if(questionCatergory.equals("Questão 4")){
                v.setQuestion4(Integer.valueOf(numericValue.getText().toString()));
                v.setQuestion5(0);
            } else
            if(questionCatergory.equals("Questão 5")){
                v.setQuestion5(Integer.valueOf(numericValue.getText().toString()));
            }
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
        /**
         * Do not clear on a null object reference
         */
        if(radioGroup!=null){
        radioGroup.clearCheck();
        }

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
