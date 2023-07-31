package mz.org.fgh.mentoring.config.dao;

import mz.org.fgh.mentoring.config.model.Setting;
import mz.org.fgh.mentoring.dao.GenericDAO;
import mz.org.fgh.mentoring.model.LifeCycleStatus;

import java.util.List;

public interface SettingDAO extends GenericDAO<Setting> {

    String TABLE_NAME = "settings";
    String FIELD_NAME = "uuid";

    class QUERY {
        public static final String findByDesignation = "SELECT s.id, s.designation, s.value, s.life_cycle_status, s.uuid FROM " + TABLE_NAME + " s " +
                "WHERE s.life_cycle_status = 'ACTIVE' and s.designation = ?;";

        public static final String findByLifeCycleStatus = "SELECT s.id, s.designation, s.value, s.uuid, s.life_cycle_status FROM settings s WHERE s.life_cycle_status = :lifeCycleStatus;";

    }

    Setting findByDesignation(final String designation);

    List<Setting> findByLifeCycleStatus(LifeCycleStatus lifeCycleStatus);
}
