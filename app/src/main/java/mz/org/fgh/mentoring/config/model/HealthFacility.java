package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 10/26/16.
 */
public class HealthFacility extends GenericEntity {

    private District district;

    private String healthFacility;

    public HealthFacility(String healthFacility) {
        this.healthFacility = healthFacility;
    }

    public HealthFacility() {
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setHealthFacility(String healthFacility) {
        this.healthFacility = healthFacility;
    }

    public District getDistrict() {
        return district;
    }

    public String getHealthFacility() {
        return healthFacility;
    }

    @Override
    public String toString() {
        return this.healthFacility;
    }
}
