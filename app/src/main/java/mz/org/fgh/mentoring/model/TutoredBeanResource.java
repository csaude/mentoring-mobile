/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.model;

import java.util.List;

public class TutoredBeanResource extends BeanResource {

    private List<Tutored> tutoreds;

    private Tutored tutored;

    private List<String> tutoredUuids;

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

    public List<String> getTutoredsUuids() {
        return tutoredUuids;
    }

    public void setTutoredUuids(List<String> tutoredUuids) {
        this.tutoredUuids = tutoredUuids;
    }
}
