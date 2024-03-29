package mz.org.fgh.mentoring.dao;

import android.content.ContentValues;
import android.database.Cursor;
import mz.org.fgh.mentoring.config.model.FormType;
import mz.org.fgh.mentoring.model.GenericEntity;

import java.util.List;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public interface GenericDAO<T extends GenericEntity> {

    String DISTRICT_TABLE = "CREATE TABLE districts(id INTEGER PRIMARY KEY, province TEXT, district TEXT, uuid TEXT UNIQUE, created_at TEXT);";

    String HEALTH_FACILITY_TABLE = "CREATE TABLE health_facilities(id INTEGER PRIMARY KEY, health_facility TEXT, district_uuid TEXT, uuid TEXT UNIQUE, created_at TEXT, FOREIGN KEY(district_uuid) REFERENCES districts(uuid));";

    String CAREER_TABLE = "CREATE TABLE careers(id INTEGER PRIMARY KEY, career_type TEXT, position TEXT, uuid TEXT UNIQUE, created_at TEXT);";

    String TUTORED_TABLE = "CREATE TABLE tutoreds(id INTEGER PRIMARY KEY, name TEXT, surname TEXT, phone_number TEXT, career_uuid TEXT, uuid TEXT UNIQUE, created_at TEXT, FOREIGN KEY(career_uuid) REFERENCES careers(uuid));";

    String FORM_TABLE = "CREATE TABLE forms(id INTEGER PRIMARY KEY, name TEXT, description TEXT, programmatic_area_uuid TEXT, version INTEGER, uuid TEXT UNIQUE, target INTEGER, created_at TEXT, updated_at TEXT);";

    String QUESTION_TABLE = "CREATE TABLE questions(id INTEGER PRIMARY KEY, question TEXT, question_type TEXT, question_category TEXT, uuid TEXT UNIQUE, created_at TEXT);";

    String FORM_QUESTION_TABLE = "CREATE TABLE form_questions(id INTEGER PRIMARY KEY, form_uuid TEXT, question_uuid TEXT, uuid TEXT UNIQUE, sequence INTEGER, created_at TEXT, updated_at TEXT, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(question_uuid) REFERENCES questions(uuid));";

    String MENTORSHIP_TABLE = "CREATE TABLE mentorships(id INTEGER PRIMARY KEY, form_uuid TEXT, tutored_uuid TEXT, health_facility_uuid TEXT, uuid TEXT UNIQUE, start_date TEXT, end_date TEXT, performed_date TEXT, created_at TEXT, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(tutored_uuid) REFERENCES tutoreds(uuid), FOREIGN KEY(health_facility_uuid) REFERENCES health_facilities(uuid));";

    String ANSWER_TABLE = "CREATE TABLE answers(id INTEGER PRIMARY KEY, form_uuid TEXT, mentorship_uuid TEXT, question_uuid TEXT, uuid TEXT UNIQUE, created_at TEXT, text_value TEXT, boolean_value INTEGER, numeric_value INTEGER, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(mentorship_uuid) REFERENCES mentorships(uuid), FOREIGN KEY(question_uuid) REFERENCES questions(uuid));";

    String INDICATOR_TABLE = "CREATE TABLE indicators(id INTEGER PRIMARY KEY, tutor_uuid TEXT, form_uuid TEXT, health_facility_uuid TEXT, uuid TEXT UNIQUE, performed_date TEXT, referred_month TEXT, created_at TEXT, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(health_facility_uuid) REFERENCES health_facilities(uuid));";

    String ALTER_FORM_TABLE = "ALTER TABLE forms ADD COLUMN form_type TEXT DEFAULT " + FormType.MENTORING + ";";

    String ALTER_ANSWER_TABLE = "ALTER TABLE answers ADD COLUMN indicator_uuid TEXT;";

    String SESSION_TABLE = "CREATE TABLE sessions(id INTEGER PRIMARY KEY, form_uuid TEXT, health_facility_uuid TEXT, uuid TEXT UNIQUE, start_date TEXT, end_date TEXT, performed_date TEXT, status TEXT, reason TEXT, created_at TEXT, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(health_facility_uuid) REFERENCES health_facilities(uuid));";

    String ALTER_MENTORSHIP_TABLE_ADD_SESSION = "ALTER TABLE mentorships ADD COLUMN session_uuid TEXT";

    String CABINET_TABLE = "CREATE TABLE cabinets(id INTEGER PRIMARY KEY, uuid TEXT UNIQUE, name TEXT, created_at TEXT, updated_at TEXT);";

    String ALTER_MENTORSHIP_TABLE_ADD_CABINET = "ALTER TABLE mentorships ADD COLUMN cabinet_uuid TEXT";

    String FORM_TARGETS_TABLE = "CREATE TABLE form_targets(id INTEGER PRIMARY KEY, form_uuid TEXT, career_uuid TEXT, target INTEGER, uuid TEXT UNIQUE, created_at TEXT);";

    String ALTER_MENTORSHIP_TABLE_ADD_ITERATION_TYPE_COLUMN = "ALTER TABLE mentorships ADD COLUMN iteration_type TEXT";

    String ALTER_MENTORSHIP_TABLE_ADD_ITERATION_NUMBER_COLUMN = "ALTER TABLE mentorships ADD COLUMN iteration_number INTEGER";

    String ALTER_FORM_TABLE_ADD_PATIENT_TARGET = "ALTER TABLE forms ADD COLUMN target_patient INTEGER";

    String ALTER_FORM_TABLE_ADD_FILE_TARGET = "ALTER TABLE forms ADD COLUMN target_file INTEGER";

    String ALTER_FORM_QUESTION_ADD_APPLICABLE = "ALTER TABLE form_questions ADD COLUMN applicable INTEGER DEFAULT 0;";

    String ALTER_TUTORED_TABLE_ADD_LIFE_CYCLE_STATUS = "ALTER TABLE tutoreds ADD COLUMN life_cycle_status TEXT";

    String ALTER_MENTORSHIP_TABLE_ADD_TIME_OF_DAY_COLUMN = "ALTER TABLE mentorships ADD COLUMN time_of_day TEXT";

    String ALTER_MENTORSHIP_TABLE_ADD_DOOR_COLUMN = "ALTER TABLE mentorships ADD COLUMN door TEXT";

    String ALTER_SESSION_TABLE_ADD_TUTORED_COLUMN = "ALTER TABLE sessions ADD COLUMN tutored_uuid TEXT";

    String SETTINGS_TABLE = "CREATE TABLE settings(id INTEGER PRIMARY KEY, designation TEXT, value TEXT, uuid TEXT UNIQUE, created_at TEXT, life_cycle_status TEXT);";

    String getTableName();

    String getFieldName();

    ContentValues getContentValues(final T entity);

    T getPopulatedEntity(Cursor cursor);

    void create(final T entity);

    void update(final T entity);

    boolean exist(final String name);

    T findByUuid(final String uuid);

    void close();

    void delete(final String whereClause, final List<String> params);
}
