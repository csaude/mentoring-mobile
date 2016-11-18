package mz.org.fgh.mentoring.helpers;

import mz.org.fgh.mentoring.config.model.Question;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class FormQuestionHelper {

    private FormHelper form;

    private Question question;

    public FormHelper getForm() {
        return form;
    }

    public void setForm(FormHelper form) {
        this.form = form;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
