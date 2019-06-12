package mz.org.fgh.mentoring.config.dao;

import mz.org.fgh.mentoring.config.model.FormTarget;
import mz.org.fgh.mentoring.dao.GenericDAO;

public interface FormTargetDAO extends GenericDAO<FormTarget> {

    String TABLE_NAME = "form_targets";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findByCareerUuid = "SELECT ft.id, ft.uuid, ft.form_uuid, ft.career_uuid, ft.target FROM " + TABLE_NAME + " ft " +
                "WHERE ft.career_uuid = ?;";
    }

    FormTarget findByCareerUuid(final String careerUuid);
}
