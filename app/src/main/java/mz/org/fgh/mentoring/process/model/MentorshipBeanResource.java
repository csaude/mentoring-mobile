package mz.org.fgh.mentoring.process.model;

import java.util.Collections;
import java.util.List;

import mz.org.fgh.mentoring.helpers.MentorshipHelper;
import mz.org.fgh.mentoring.model.BeanResource;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public class MentorshipBeanResource extends BeanResource {

    private List<MentorshipHelper> mentorships;

    private List<String> mentorshipUuids;

    public MentorshipBeanResource() {
    }

    public void setMentorships(List<MentorshipHelper> mentorships) {
        this.mentorships = mentorships;
    }

    public List<MentorshipHelper> getMentorships() {
        return Collections.unmodifiableList(mentorships);
    }

    public List<String> getMentorshipUuids() {
        return mentorshipUuids;
    }
}
