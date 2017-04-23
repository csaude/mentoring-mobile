/*
 * Friends in Global Health - FGH © 2016
 */

package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class FormQuestion extends GenericEntity {

    private Form form;

    private Question question;

    public Form getForm() {
        return form;
    }

    public Question getQuestion() {
        return question;
    }
}
