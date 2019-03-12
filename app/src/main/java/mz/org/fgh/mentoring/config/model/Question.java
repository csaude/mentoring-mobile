package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Question extends GenericEntity {

    private String question;

    private QuestionType questionType;

    private QuestionCategory questionsCategory;

    public Question() {
    }

    public Question(String uuid, String question, QuestionType questionType, QuestionCategory questionsCategory) {
        this.setUuid(uuid);
        this.question = question;
        this.questionType = questionType;
        this.questionsCategory = questionsCategory;
    }

    public String getQuestion() {
        return question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public QuestionCategory getQuestionsCategory() {
        return questionsCategory;
    }
}
