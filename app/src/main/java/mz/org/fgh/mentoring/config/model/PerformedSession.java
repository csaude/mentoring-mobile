package mz.org.fgh.mentoring.config.model;

import java.io.Serializable;

public class PerformedSession implements Serializable {

    private String district;

    private String healthFacility;

    private String formName;

    private int totalPerformed;


    public PerformedSession() {
    }

    public PerformedSession(String district, String healthFacility, int totalPerformed) {
        this.district = district;
        this.healthFacility = healthFacility;
        this.totalPerformed = totalPerformed;
    }

    public String getDistrict() {
        return district;
    }

    public String getHealthFacility() {
        return healthFacility;
    }

    public int getTotalPerformed() {
        return totalPerformed;
    }

    public String getFormName() {
        return formName;
    }
}
