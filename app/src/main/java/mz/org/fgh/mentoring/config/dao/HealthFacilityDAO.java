package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.dao.GenericDAO;
import mz.org.fgh.mentoring.config.model.HealthFacility;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public interface HealthFacilityDAO extends GenericDAO<HealthFacility> {

    class QUERY {
        public static final String findAll = "SELECT hf.id, hf.health_facility, hf.district_id, d.district FROM health_facilities hf INNER JOIN districts d ON hf.district_id = d.id;";
    }

    List<HealthFacility> findAll();
}
