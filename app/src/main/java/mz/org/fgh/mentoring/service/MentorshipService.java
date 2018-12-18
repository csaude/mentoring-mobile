package mz.org.fgh.mentoring.service;

import java.time.LocalDate;
import java.util.List;

import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by steliomo on 2/7/18.
 */

public interface MentorshipService {

    Mentorship createMentorship(Mentorship mentorship);

    List<Mentorship> findMentorshipsBySession(Session session);

    List<Mentorship> findMentorshipsByUserAndPerformedDate(final UserContext context,
                                                           final LocalDate performedStartDate,
                                                           final LocalDate performedEndDate);

    void deleteBySessionUuid(List<String> sessionUuids);
}
