package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

import java.time.LocalDateTime;
import java.util.Date;

public class Setting extends GenericEntity {

    private String designation;

    private String uuid;

    private Date value;

    public Setting() {
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "designation='" + designation + '\'' +
                ", uuid='" + uuid + '\'' +
                ", value=" + value +
                '}';
    }
}
