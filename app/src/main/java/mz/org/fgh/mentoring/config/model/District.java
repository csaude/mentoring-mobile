package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 10/26/16.
 */
public class District extends GenericEntity {

    private String province;

    private String district;

    public District() {
    }

    public District(final String uuid) {
        this.setUuid(uuid);
    }

    public District(final Long id, final String uuid, final String province, final String district) {
        this.setId(id);
        this.setUuid(uuid);
        this.province = province;
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return this.district;
    }
}
