package mz.org.fgh.mentoring.dto;

import mz.org.fgh.mentoring.config.model.Question;

/**
 * Created by Stélio Moiane on 4/11/17.
 */
public class AnswerHelper {

    private String questionUuid;

    private String answerUuid;

    private String value;

    private Question question;

    public AnswerHelper(final String questionUuid, final String answerUuid, final String value) {
        this.questionUuid = questionUuid;
        this.answerUuid = answerUuid;
        this.value = value;
    }

    public String getQuestionUuid() {
        return questionUuid;
    }

    public String getAnswerUuid() {
        return answerUuid;
    }

    public String getValue() {
        return value;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}

