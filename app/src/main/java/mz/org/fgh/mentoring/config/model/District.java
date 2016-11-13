package mz.org.fgh.mentoring.config.model;

/**
 * Created by Stélio Moiane on 10/26/16.
 */
public class District {

    private Long id;
    private String province;
    private String district;

    public District(final Long id, final String province, final String district) {
        this.id = id;
        this.province = province;
        this.district = district;
    }

    public Long getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    @Override
    public String toString() {
        return this.district;
    }
}
