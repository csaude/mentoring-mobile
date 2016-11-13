package mz.org.fgh.mentoring.config.model;

/**
 * Created by Stélio Moiane on 10/26/16.
 */
public class HealthFacility {

    private Long id;
    private District district;
    private Long districtId;

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getId() {
        return id;
    }

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
