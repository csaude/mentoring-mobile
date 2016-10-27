package mz.org.fgh.mentoring.model;

/**
 * Created by Stélio Moiane on 10/26/16.
 */
public class HealthFacility {

    private Long id;
    private District district;
    private String healthFacility;

    public Long getId() {
        return id;
    }

    public District getDistrict() {
        return district;
    }

    public String getHealthFacility() {
        return healthFacility;
    }
}
