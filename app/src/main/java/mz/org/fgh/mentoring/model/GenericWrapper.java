package mz.org.fgh.mentoring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Cabinet;
import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.FormTarget;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.PerformedSession;

/**
 * Created by Stélio Moiane on 10/25/16.
 */
public class GenericWrapper {

    private List<HealthFacility> healthFacilities;

    private List<Career> careers;

    private List<FormQuestion> formQuestions;

    private List<Tutored> tutoreds;

    private List<Cabinet> cabinets;

    private List<FormTarget> formTargets;

    @JsonProperty("performedSession")
    private List<PerformedSession> performedSessions;

    public List<HealthFacility> getHealthFacilities() {
        return this.healthFacilities;
    }

    public List<Career> getCareers() {
        return this.careers;
    }

    public List<FormQuestion> getFormQuestions() {
        return this.formQuestions;
    }

    public List<Tutored> getTutoreds() {
        return tutoreds;
    }

    public List<Cabinet> getCabinets() {
        return cabinets;
    }

    public List<PerformedSession> getPerformedSessions() {
        return performedSessions;
    }

    public List<FormTarget> getFormTargets() {
        return formTargets;
    }
}
