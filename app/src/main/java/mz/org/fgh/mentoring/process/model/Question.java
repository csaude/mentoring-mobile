package mz.org.fgh.mentoring.process.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Question extends GenericEntity {

    public Question(String code, String question, QuestionType questionType, QuestionCategory questionCategory) {
        this.code = code;
        this.question = question;
        this.questionType = questionType;
        this.questionCategory = questionCategory;
    }

    private String code;

    private String question;

    private QuestionType questionType;

    private QuestionCategory questionCategory;

    public String getCode() {
        return code;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }
}
