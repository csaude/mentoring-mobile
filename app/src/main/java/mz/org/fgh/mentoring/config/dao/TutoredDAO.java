package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.dao.GenericDAO;
import mz.org.fgh.mentoring.model.LifeCycleStatus;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public interface TutoredDAO extends GenericDAO<Tutored> {

    String TABLE_NAME = "tutoreds";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findByLifeCycleStatus = "SELECT t.id, t.name, t.surname, t.phone_number, c.position, t.career_uuid, t.uuid, t.life_cycle_status FROM tutoreds t INNER JOIN careers c ON t.career_uuid=c.uuid WHERE t.life_cycle_status = :lifeCycleStatus ORDER BY t.name, t.surname ASC;";
    }

    List<Tutored> findByLifeCycleStatus(LifeCycleStatus lifeCycleStatus);
}
