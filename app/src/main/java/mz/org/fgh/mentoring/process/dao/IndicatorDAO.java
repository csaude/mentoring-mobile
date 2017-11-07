package mz.org.fgh.mentoring.process.dao;

import java.util.List;

import mz.org.fgh.mentoring.dao.GenericDAO;
import mz.org.fgh.mentoring.process.model.Indicator;
import mz.org.fgh.mentoring.process.model.Mentorship;

/**
 * Created by steliomo on 10/30/17.
 */

public interface IndicatorDAO extends GenericDAO<Indicator> {

    String TABLE_NAME = "indicators";
    String FIELD_NAME = "uuid";

    class QUERY {

        public static final String findAll = "SELECT i.id, i.uuid, i.form_uuid, i.tutor_uuid, i.health_facility_uuid, f.name as form_name, " +
                "hf.health_facility as health_facility, i.performed_date, i.referred_month, i.created_at " +
                "FROM " + TABLE_NAME + " i " +
                "INNER JOIN forms f ON i.form_uuid = f.uuid " +
                "INNER JOIN health_facilities hf ON i.health_facility_uuid = hf.uuid";
    }

    List<Indicator> findAll();

    void delete(List<String> uuids);
}
