package mz.org.fgh.mentoring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import mz.org.fgh.mentoring.config.model.*;
import mz.org.fgh.mentoring.process.model.Mentorship;

import java.util.List;

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

    private List<Setting> settings;


    private Setting setting;

    @JsonProperty("performedSession")
    private List<PerformedSession> performedSessions;

    @JsonProperty("mentorship")
    private List<Mentorship> mentorships;

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

    public List<Mentorship> getMentorships() {
        return mentorships;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
