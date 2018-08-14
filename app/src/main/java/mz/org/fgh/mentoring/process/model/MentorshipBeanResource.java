package mz.org.fgh.mentoring.process.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.org.fgh.mentoring.dto.SessionDTO;
import mz.org.fgh.mentoring.model.BeanResource;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public class MentorshipBeanResource extends BeanResource {

    private List<SessionDTO> sessions;

    private List<String> sessionUuids;

    public MentorshipBeanResource() {
        this.sessions = new ArrayList<>();
    }

    public void addSessions(SessionDTO sessionDTO) {
        this.sessions.add(sessionDTO);
    }

    public List<SessionDTO> getSessions() {
        return this.sessions;
    }

    public List<String> getSessionUuids() {
        return sessionUuids;
    }
}
