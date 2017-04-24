/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;
import mz.org.fgh.mentoring.process.model.Mentorship;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public abstract class Answer extends GenericEntity {

    private Form form;

    private Mentorship mentorship;

    private Question question;

    public abstract void setValue(String value);

    public abstract String getValue();

    public Form getForm() {
        return this.form;
    }

    public void setForm(final Form form) {
        this.form = form;
    }

    public Mentorship getMentorship() {
        return this.mentorship;
    }

    public void setMentorship(final Mentorship mentorship) {
        this.mentorship = mentorship;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(final Question question) {
        this.question = question;
    }
}
