package mz.org.fgh.mentoring.process.model;

import java.util.Date;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by steliomo on 10/30/17.
 */

public class Indicator extends GenericEntity {

    private Tutor tutor;
    private Form form;
    private HealthFacility healthFacility;
    private String performedDate;
    private String referredMonth;

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
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

    public String getPerformedDate() {
        return performedDate;
    }

    public void setPerformedDate(String performedDate) {
        this.performedDate = performedDate;
    }

    public String getReferredMonth() {
        return referredMonth;
    }

    public void setReferredMonth(String referredMonth) {
        this.referredMonth = referredMonth;
    }
}
