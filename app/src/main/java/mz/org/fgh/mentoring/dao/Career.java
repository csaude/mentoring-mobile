package mz.org.fgh.mentoring.dao;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public class Career {

    private Long id;

    private String careerType;

    private String position;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCareerType() {
        return careerType;
    }

    public void setCareerType(String careerType) {
        this.careerType = careerType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
