package mz.org.fgh.mentoring.dao;

import android.content.ContentValues;
import android.database.Cursor;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public interface GenericDAO<T extends GenericEntity> {

    String DISTRICT_TABLE = "CREATE TABLE districts(id INTEGER PRIMARY KEY, province TEXT, district TEXT, uuid TEXT, created_at TEXT);";

    String HEALTH_FACILITY_TABLE = "CREATE TABLE health_facilities(id INTEGER PRIMARY KEY, health_facility TEXT, district_id INTEGER, uuid TEXT, created_at TEXT, FOREIGN KEY(district_id) REFERENCES districts(id));";

    String CAREER_TABLE = "CREATE TABLE careers(id INTEGER PRIMARY KEY, career_type TEXT, position TEXT, uuid TEXT, created_at TEXT);";

    String TUTORED_TABLE = "CREATE TABLE tutoreds(id INTEGER PRIMARY KEY, code TEXT, name TEXT, surname TEXT, phoneNumber TEXT, carrer_id INTEGER, uuid TEXT, created_at TEXT, FOREIGN KEY(carrer_id) REFERENCES careers(id));";

    String FORM_TABLE = "CREATE TABLE forms(id INTEGER PRIMARY KEY, code TEXT, name TEXT, description TEXT, programmatic_area TEXT, version INTEGER, uuid TEXT, created_at TEXT, CONSTRAINT code_unique UNIQUE (code));";

    String QUESTION_TABLE = "CREATE TABLE questions(id INTEGER PRIMARY KEY, code TEXT, question TEXT, question_type TEXT, question_category TEXT, uuid TEXT, created_at TEXT, CONSTRAINT code_unique UNIQUE (code));";

    String FORM_QUESTION_TABLE = "CREATE TABLE form_questions(id INTEGER PRIMARY KEY, form_code TEXT, question_code TEXT, uuid TEXT, created_at TEXT, FOREIGN KEY(form_code) REFERENCES forms(code), FOREIGN KEY(question_code) REFERENCES questions(code));";

    String MENTORSHIP_TABLE = "CREATE TABLE mentorships(id INTEGER PRIMARY KEY, form_uuid TEXT, tutored_uuid TEXT, health_facility_uuid TEXT, uuid TEXT, start_date TEXT, end_date TEXT, created_at TEXT, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(tutored_uuid) REFERENCES tutoreds(uuid), FOREIGN KEY(health_facility_uuid) REFERENCES health_facilities(uuid));";

    String ANSWER_TABLE = "CREATE TABLE answers(id INTEGER PRIMARY KEY, form_uuid TEXT, mentorship_uuid TEXT, question_uuid TEXT, uuid TEXT, created_at TEXT, text_value TEXT, boolean_value INTEGER, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(mentorship_uuid) REFERENCES mentorships(uuid), FOREIGN KEY(question_uuid) REFERENCES questions(uuid));";

    String getTableName();

    String getFieldName();

    ContentValues getContentValues(final T entity);

    T getPopulatedEntity(Cursor cursor);

    void create(final T entity);

    void update(final T entity);

    boolean exist(final String name);

    T findByUuid(final String uuid);

    void close();
}
