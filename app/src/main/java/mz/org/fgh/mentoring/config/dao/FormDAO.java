package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.dao.GenericDAO;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public interface FormDAO extends GenericDAO<Form> {

    String TABLE_NAME = "forms";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findAll = "SELECT * FROM " + TABLE_NAME;
    }

    List<Form> findAll();
}
