package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 10/26/16.
 */
public class District extends GenericEntity {

    private String province;

    private String district;

    public District(final Long id, final String province, final String district) {
        this.setId(id);
        this.province = province;
        this.district = district;
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
