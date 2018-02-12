package mz.org.fgh.mentoring.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by steliomo on 2/8/18.
 */

public class SessionDTO {

    private Session session;

    private List<MentorshipHelper> mentorships;

    public SessionDTO() {
        this.mentorships = new ArrayList<>();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void addMentoshipHelper(MentorshipHelper mentorshipHelper) {
        mentorships.add(mentorshipHelper);
    }

    public List<MentorshipHelper> getMentorships() {
        return Collections.unmodifiableList(this.mentorships);
    }
}
