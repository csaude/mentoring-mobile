package mz.org.fgh.mentoring.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public interface TutoredDao extends  GenericDAO<Tutored> {
    class QUERY {
        public static final String findAll = "SELECT t.id, t.name, t.surname, t.phoneNumber FROM tutoreds t;";
    }

    List<Tutored> findAll();
}
