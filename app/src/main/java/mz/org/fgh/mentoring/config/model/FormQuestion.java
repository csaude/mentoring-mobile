/*
 * Friends in Global Health - FGH © 2016
 */

package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class FormQuestion extends GenericEntity {

    private String formCode;

    private String questionCode;

    public FormQuestion(String formCode, String questionCode) {
        this.formCode = formCode;
        this.questionCode = questionCode;
    }

    public String getFormCode() {
        return formCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }
}
