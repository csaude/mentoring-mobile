package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.dao.GenericDAO;

/**
 * Created by Stélio Moiane on 3/30/17.
 */
public interface AnswerDAO extends GenericDAO<Answer> {

    String TABLE_NAME = "answers";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findByMentorshipUuid = "SELECT a.form_uuid, a.mentorship_uuid, a.question_uuid, a.uuid, a.text_value, a.boolean_value, q.question_type, q.code FROM " + TABLE_NAME + " a " +
                "INNER JOIN questions q ON a.question_uuid = q.uuid " +
                "WHERE a.mentorship_uuid = ?;";

        public static final String deleteByMentorshipUuids = "DELETE FROM " + TABLE_NAME + " a " +
                "INNER JOIN mentorships m on a.mentorship_uuid = m.uuid " +
                "WHERE m.uuid in (?);";
    }

    List<Answer> findByMentorshipUuid(final String mentorshipUuid);

    void deleteByMentorshipUuids(final List<String> mentorshipsUuids);
}
