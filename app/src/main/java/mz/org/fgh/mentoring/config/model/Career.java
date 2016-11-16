package mz.org.fgh.mentoring.config.model;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public class Career {

    private Long id;

    private CareerType careerType;

    private String position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
