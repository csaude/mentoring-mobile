package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

public class QuestionCategory extends GenericEntity {

    public static final String FEEDBACK_QUESTIONS = "Questões de feedback";

    private String category;

    public QuestionCategory() {
    }

    public QuestionCategory(final String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
