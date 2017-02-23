/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TutoredBeanResource {

    @SerializedName("userContext")
    private UserContext userContext;

    @SerializedName("tutoreds")
    private List<Tutored> tutoreds;

    @SerializedName("tutored")
    private Tutored tutored;


    public TutoredBeanResource() {
    }

    public UserContext getUserContext() {
        return this.userContext;
    }

    public void setUserContext(final UserContext userContext) {
        this.userContext = userContext;
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
