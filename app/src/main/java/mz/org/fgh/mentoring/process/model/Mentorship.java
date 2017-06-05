/*
 * Friends in Global Health - FGH © 2016
 */

package mz.org.fgh.mentoring.process.model;

import java.util.Date;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Tutor;
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

    private Tutor tutor;

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

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
