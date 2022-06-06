package mz.org.fgh.mentoring.process.dao;

import java.util.List;

import mz.org.fgh.mentoring.dao.GenericDAO;
import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by steliomo on 2/6/18.
 */

public interface SessionDAO extends GenericDAO<Session> {

    String TABLE_NAME = "sessions";
    String FIELD_NAME = "uuid";

    class QUERY {

        public static final String findAll = "SELECT s.id, s.uuid, s.form_uuid, s.health_facility_uuid, f.name as form_name, s.tutored_uuid, t.name || ' ' || t.surname as tutored_name, " +
                "hf.health_facility as health_facility, s.start_date, s.end_date, s.performed_date, s.status, s.reason, s.created_at, f.target_patient, f.target_file " +
                "FROM " + TABLE_NAME + " s " +
                "INNER JOIN forms f ON s.form_uuid = f.uuid " +
                "INNER JOIN tutoreds t ON s.tutored_uuid = t.uuid " +
                "INNER JOIN health_facilities hf ON s.health_facility_uuid = hf.uuid";

        public static final String findAllToSync = "SELECT s.id, s.uuid, s.form_uuid, s.health_facility_uuid, f.name as form_name, s.tutored_uuid, t.name || ' ' || t.surname as tutored_name, " +
                "hf.health_facility as health_facility, s.start_date, s.end_date, s.performed_date, s.status, s.reason, s.created_at, f.target_patient, f.target_file " +
                "FROM " + TABLE_NAME + " s " +
                "INNER JOIN forms f ON s.form_uuid = f.uuid " +
                "INNER JOIN tutoreds t ON s.tutored_uuid = t.uuid " +
                "INNER JOIN health_facilities hf ON s.health_facility_uuid = hf.uuid ORDER BY s.id ASC LIMIT 5";


        public static final String deleteByUuids = "DELETE s.* FROM sessions s WHERE s.uuid IN (?)";
    }

    List<Session> findAll();

    List<Session> findAllToSync();

    List<Session> findSessionsByUuids(String uuids);

    void deleteByUuids(List<String> uuids);

}
