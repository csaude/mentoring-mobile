package mz.org.fgh.mentoring.service;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.config.dao.AnswerDAO;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.process.dao.MentorshipDAO;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by steliomo on 2/7/18.
 */

public class MentorshipServiceImpl implements MentorshipService {

    @Inject
    MentorshipDAO mentorshipDAO;

    @Inject
    AnswerDAO answerDAO;

    @Inject
    public MentorshipServiceImpl() {
    }

    @Override
    public Mentorship createMentorship(Mentorship mentorship) {
        this.mentorshipDAO.create(mentorship);

        for (Answer answer : mentorship.getAnswers()) {
            answer.setMentorship(mentorship);
            answer.setForm(mentorship.getForm());
            this.answerDAO.create(answer);
        }

        return mentorship;
    }

    @Override
    public List<Mentorship> findMentorshipsBySession(Session session) {
        return mentorshipDAO.findBySession(session);
    }

    @Override
    public List<Mentorship> findMentorshipsByUserAndPerformedDate(final UserContext context,
                                                                  final LocalDate performedStartDate,
                                                                  final LocalDate performedEndDate) {
        return null;
    }

    @Override
    public void deleteBySessionUuid(List<String> sessionUuids) {
        answerDAO.deleteBySessionUuids(sessionUuids);
        mentorshipDAO.deleteBySessionUuids(sessionUuids);
    }
}
