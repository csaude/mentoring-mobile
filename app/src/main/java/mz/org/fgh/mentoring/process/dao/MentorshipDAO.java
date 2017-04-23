package mz.org.fgh.mentoring.process.dao;

import java.util.List;

import mz.org.fgh.mentoring.dao.GenericDAO;
import mz.org.fgh.mentoring.process.model.Mentorship;

/**
 * Created by Stélio Moiane on 3/28/17.
 */
public interface MentorshipDAO extends GenericDAO<Mentorship> {

    String TABLE_NAME = "mentorships";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findAll = "SELECT m.id, m.uuid, m.form_uuid, m.tutored_uuid, m.health_facility_uuid, f.name as form_name, " +
                "t.name as tutored_name, t.surname as tutored_surname, t.phone_number as tutored_phone_number, " +
                "hf.health_facility as health_facility, m.start_date, m.end_date, m.created_at, " +
                "c.uuid as tutored_career_uuid " +
                "FROM " + TABLE_NAME + " m " +
                "INNER JOIN forms f ON m.form_uuid = f.uuid " +
                "INNER JOIN tutoreds t ON m.tutored_uuid = t.uuid " +
                "INNER JOIN careers c ON t.career_uuid = c.uuid " +
                "INNER JOIN health_facilities hf ON m.health_facility_uuid = hf.uuid";
    }

    List<Mentorship> findAll();

    void deleteByUuids(final List<String> uuids);
}
