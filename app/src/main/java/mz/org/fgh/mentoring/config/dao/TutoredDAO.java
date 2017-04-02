package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.dao.GenericDAO;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public interface TutoredDAO extends GenericDAO<Tutored> {

    String TABLE_NAME = "tutoreds";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findAll = "SELECT t.id, t.code, t.name, t.surname, t.phoneNumber, c.position, t.carrer_id, t.uuid FROM tutoreds t INNER JOIN careers c ON t.carrer_id=c.id;";
        public static final String findAllWithNoCode = "SELECT t.id, t.code, t.name, t.surname, t.phoneNumber, c.position, t.carrer_id, t.uuid FROM tutoreds t INNER JOIN careers c ON t.carrer_id=c.id where t.code is null;";
    }

    List<Tutored> findAll();

    List<Tutored> findAllWithNoCode();
}
