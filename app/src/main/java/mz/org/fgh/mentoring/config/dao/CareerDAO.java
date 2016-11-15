package mz.org.fgh.mentoring.config.dao;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.dao.GenericDAO;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public interface CareerDAO extends GenericDAO<Career> {

    class QUERY {
        public static final String findAll = "SELECT * FROM careers;";
        public static final String exist = "SELECT * FROM careers c WHERE c.career_type = ? AND c.position = ?;";
    }

    List<Career> findAll();

    boolean exist(final String carrerType, final String position);
}