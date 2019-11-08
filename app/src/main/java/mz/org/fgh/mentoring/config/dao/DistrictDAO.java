package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.District;
import mz.org.fgh.mentoring.dao.GenericDAO;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public interface DistrictDAO extends GenericDAO<District> {

    String TABLE_NAME = "districts";

    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findAll = "SELECT * FROM districts;";
    }

    List<District> findAll();

}
