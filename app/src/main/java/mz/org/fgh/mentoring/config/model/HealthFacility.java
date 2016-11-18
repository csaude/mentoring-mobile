package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 10/26/16.
 */
public class HealthFacility extends GenericEntity {

    private District district;

    private Long districtId;

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public void setHealthFacility(String healthFacility) {
        this.healthFacility = healthFacility;
    }

    private String healthFacility;

    public District getDistrict() {
        return district;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public String getHealthFacility() {
        return healthFacility;
    }
}
