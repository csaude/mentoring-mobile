package mz.org.fgh.mentoring.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.helpers.FormQuestionHelper;

/**
 * Created by Stélio Moiane on 10/25/16.
 */
public class GenericWrapper {

    @SerializedName("healthFacility")
    private List<HealthFacility> healthFacilities;

    @SerializedName("career")
    private List<Career> careers;

    @SerializedName("formQuestion")
    private List<FormQuestionHelper> formQuestions;

    public List<HealthFacility> getHealthFacilities() {
        return this.healthFacilities;
    }

    public List<Career> getCareers() {
        return this.careers;
    }

    public List<FormQuestionHelper> getFormQuestions() {
        return this.formQuestions;
    }
}
