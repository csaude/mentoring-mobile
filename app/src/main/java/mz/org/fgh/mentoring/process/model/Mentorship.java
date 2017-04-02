/*
 * Friends in Global Health - FGH © 2016
 */

package mz.org.fgh.mentoring.process.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.model.GenericEntity;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Mentorship extends GenericEntity {

    private Date startDate;

    private Date endDate;

    private Tutored tutored;

    private Form form;

    private HealthFacility healthFacility;

    private List<Answer> answers;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Tutored getTutored() {
        return tutored;
    }

    public void setTutored(Tutored tutored) {
        this.tutored = tutored;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public HealthFacility getHealthFacility() {
        return healthFacility;
    }

    public void setHealthFacility(HealthFacility healthFacility) {
        this.healthFacility = healthFacility;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(answers);
    }
}
