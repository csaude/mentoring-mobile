package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by steliomo on 2/7/18.
 */

public interface MentorshipService {

    Mentorship createMentorship(Mentorship mentorship);

    List<Mentorship> findMentorshipsBySession(Session session);

    void deleteBySessionUuid(List<String> sessionUuids);
}
