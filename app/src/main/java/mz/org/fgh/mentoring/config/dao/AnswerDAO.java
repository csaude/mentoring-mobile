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
        public static final String findByMentorshipUuid = "SELECT a.form_uuid, a.mentorship_uuid, a.indicator_uuid, a.question_uuid, a.uuid, a.text_value, a.boolean_value, a.numeric_value, q.question_type FROM " + TABLE_NAME + " a " +
                "INNER JOIN questions q ON a.question_uuid = q.uuid " +
                "WHERE a.mentorship_uuid = ?;";

        public static final String deleteBySessionUuids = "DELETE FROM " + TABLE_NAME + " " +
                "WHERE mentorship_uuid IN ( SELECT m.uuid FROM mentorships m WHERE m.session_uuid = ?);";

        public static final String findByIndicatorUuid = "SELECT a.form_uuid, a.mentorship_uuid, a.indicator_uuid, a.question_uuid, a.uuid, a.text_value, a.boolean_value, a.numeric_value, q.question_type FROM " + TABLE_NAME + " a " +
                "INNER JOIN questions q ON a.question_uuid = q.uuid " +
                "WHERE a.indicator_uuid = ?;";

    }

    List<Answer> findByMentorshipUuid(final String mentorshipUuid);

    void deleteBySessionUuids(final List<String> sessionUuids);

    List<Answer> findByIndicatorUuid(final String indicatorUuid);

    void deleteByIndicatorUuids(final List<String> indicatorsUuids);
}
