package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.dao.GenericDAO;

/**
 * Created by Stélio Moiane on 11/17/16.
 */
public interface FormQuestionDAO extends GenericDAO<FormQuestion> {

    String TABLE_NAME = "form_questions";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findByFormUuid = "Select fq.id, fq.uuid, fq.question_uuid, fq.form_uuid, fq.sequence, q.question, q.question_type, q.question_category, fq.created_at from " + TABLE_NAME + " fq INNER JOIN questions q ON q.uuid = fq.question_uuid " +
                "WHERE fq.form_uuid = ?;";
    }

    List<FormQuestion> findByFormUuid(String formUuid);

}
