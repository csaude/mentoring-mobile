package mz.org.fgh.mentoring.config.dao;

import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.dao.GenericDAO;

/**
 * Created by Stélio Moiane on 11/17/16.
 */
public interface FormQuestionDAO extends GenericDAO<FormQuestion> {

    String TABLE_NAME = "form_questions";
    String FIELD_NAME = "form_uuid";

}
