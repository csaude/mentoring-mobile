package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Question extends GenericEntity {

    private String question;

    private QuestionType questionType;

    private QuestionCategory questionCategory;

    public Question() {
    }

    public Question(String uuid, String code, String question, QuestionType questionType, QuestionCategory questionCategory) {
        this.setUuid(uuid);
        this.setCode(code);
        this.question = question;
        this.questionType = questionType;
        this.questionCategory = questionCategory;
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
