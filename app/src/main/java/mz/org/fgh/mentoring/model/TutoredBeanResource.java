/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TutoredBeanResource extends BeanResource {

    @SerializedName("tutoreds")
    private List<Tutored> tutoreds;

    @SerializedName("tutored")
    private Tutored tutored;


    public TutoredBeanResource() {
    }


    public void setTutoreds(final List<Tutored> tutoreds) {
        this.tutoreds = tutoreds;
    }

    public List<Tutored> getTutoreds() {
        return this.tutoreds;
    }

    public Tutored getTutored() {
        return tutored;
    }

    public void setTutored(Tutored tutored) {
        this.tutored = tutored;
    }
}
