package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.dao.GenericDAO;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public interface TutoredDAO extends GenericDAO<Tutored> {

    String TABLE_NAME = "tutoreds";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findAll = "SELECT t.id, t.name, t.surname, t.phone_number, c.position, t.career_uuid, t.uuid FROM tutoreds t INNER JOIN careers c ON t.career_uuid=c.uuid;";
    }

    List<Tutored> findAll();
}
