package mz.org.fgh.mentoring.process.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

import mz.org.fgh.mentoring.model.BeanResource;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public class MentorshipBeanResource extends BeanResource {

    @SerializedName("mentorships")
    private List<Mentorship> mentorships;

    public void setMentorships(List<Mentorship> mentorships) {
        this.mentorships = mentorships;
    }

    public List<Mentorship> getMentorships() {
        return Collections.unmodifiableList(mentorships);
    }
}
