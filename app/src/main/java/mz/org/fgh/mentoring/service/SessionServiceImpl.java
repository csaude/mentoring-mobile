package mz.org.fgh.mentoring.service;

import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.process.dao.MentorshipDAO;
import mz.org.fgh.mentoring.process.dao.SessionDAO;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by steliomo on 2/6/18.
 */

public class SessionServiceImpl implements SessionService {

    @Inject
    SessionDAO sessionDAO;

    @Inject
    MentorshipService mentorshipService;

    @Inject
    public SessionServiceImpl() {
    }

    @Override
    public Session createSession(Session session) {
        sessionDAO.create(session);

        for (Mentorship mentorship : session.getMentorships()) {
            mentorship.setSession(session);
            mentorshipService.createMentorship(mentorship);
        }

        return session;
    }

    @Override
    public List<Session> findAllSessions() {
        return sessionDAO.findAll();
    }

    @Override
    public void deleteSessionsByUuids(List<String> uuids) {
        mentorshipService.deleteBySessionUuid(uuids);
        sessionDAO.deleteByUuids(uuids);
    }
}
