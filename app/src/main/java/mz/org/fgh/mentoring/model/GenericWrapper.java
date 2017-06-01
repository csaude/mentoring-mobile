package mz.org.fgh.mentoring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.HealthFacility;

/**
 * Created by Stélio Moiane on 10/25/16.
 */
public class GenericWrapper {

    @JsonProperty("healthFacility")
    private List<HealthFacility> healthFacilities;

    @JsonProperty("career")
    private List<Career> careers;

    @JsonProperty("formQuestion")
    private List<FormQuestion> formQuestions;

    @JsonProperty("tutored")
    private List<Tutored> tutoreds;

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
}
