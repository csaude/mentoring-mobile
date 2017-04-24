package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public class Career extends GenericEntity {

    private CareerType careerType;

    private String position;

    public Career() {
    }

    public Career(final String uuid) {
        this.setUuid(uuid);
    }

    public CareerType getCareerType() {
        return careerType;
    }

    public void setCareerType(CareerType careerType) {
        this.careerType = careerType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return getPosition();
    }

}
