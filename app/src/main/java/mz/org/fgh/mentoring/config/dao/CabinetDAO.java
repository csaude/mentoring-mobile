package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Cabinet;
import mz.org.fgh.mentoring.dao.GenericDAO;

/**
 * Created by steliomo on 4/12/18.
 */

public interface CabinetDAO extends GenericDAO<Cabinet> {

    String TABLE_NAME = "cabinets";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findAll = "Select c.uuid, c.name, c.created_at from " + TABLE_NAME + " c;";
    }

    List<Cabinet> findAll();
}
