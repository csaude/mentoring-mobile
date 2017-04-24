package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.dao.GenericDAO;

/**
 * Created by Stélio Moiane on 11/17/16.
 */
public interface QuestionDAO extends GenericDAO<Question> {

    String TABLE_NAME = "questions";
    String FIELD_NAME = "code";

    class QUERY {

        public static final String findQuestionByForm = "SELECT q.id, q.uuid, q.code, q.question, q.question_type, q.question_category FROM " + TABLE_NAME + " q " +
                "INNER JOIN form_questions fq ON q.uuid = fq.question_uuid " +
                "INNER JOIN forms f on f.uuid = fq.form_uuid " +
                "WHERE f.uuid = ?;";
    }

    List<Question> findQuestionByForm(final String uuid);
}
