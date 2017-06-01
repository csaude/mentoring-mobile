package mz.org.fgh.mentoring.model;

import mz.org.fgh.mentoring.config.model.Tutor;

/**
 * Created by Stélio Moiane on 5/26/17.
 */
public class TutorBeanResource extends BeanResource {

    private Tutor tutor;

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Tutor getTutor() {
        return tutor;
    }
}
