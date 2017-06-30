package mz.org.fgh.mentoring.dao;

import android.content.ContentValues;
import android.database.Cursor;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public interface GenericDAO<T extends GenericEntity> {

    String DISTRICT_TABLE = "CREATE TABLE districts(id INTEGER PRIMARY KEY, province TEXT, district TEXT, uuid TEXT UNIQUE, created_at TEXT);";

    String HEALTH_FACILITY_TABLE = "CREATE TABLE health_facilities(id INTEGER PRIMARY KEY, health_facility TEXT, district_uuid TEXT, uuid TEXT UNIQUE, created_at TEXT, FOREIGN KEY(district_uuid) REFERENCES districts(uuid));";

    String CAREER_TABLE = "CREATE TABLE careers(id INTEGER PRIMARY KEY, career_type TEXT, position TEXT, uuid TEXT UNIQUE, created_at TEXT);";

    String TUTORED_TABLE = "CREATE TABLE tutoreds(id INTEGER PRIMARY KEY, name TEXT, surname TEXT, phone_number TEXT, career_uuid TEXT, uuid TEXT UNIQUE, created_at TEXT, FOREIGN KEY(career_uuid) REFERENCES careers(uuid));";

    String FORM_TABLE = "CREATE TABLE forms(id INTEGER PRIMARY KEY, name TEXT, description TEXT, programmatic_area_uuid TEXT, version INTEGER, uuid TEXT UNIQUE, created_at TEXT);";

    String QUESTION_TABLE = "CREATE TABLE questions(id INTEGER PRIMARY KEY, question TEXT, question_type TEXT, question_category TEXT, uuid TEXT UNIQUE, created_at TEXT);";

    String FORM_QUESTION_TABLE = "CREATE TABLE form_questions(id INTEGER PRIMARY KEY, form_uuid TEXT, question_uuid TEXT, uuid TEXT UNIQUE, created_at TEXT, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(question_uuid) REFERENCES questions(uuid));";

    String MENTORSHIP_TABLE = "CREATE TABLE mentorships(id INTEGER PRIMARY KEY, form_uuid TEXT, tutored_uuid TEXT, health_facility_uuid TEXT, uuid TEXT UNIQUE, start_date TEXT, end_date TEXT, performed_date TEXT, referred_month TEXT, created_at TEXT, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(tutored_uuid) REFERENCES tutoreds(uuid), FOREIGN KEY(health_facility_uuid) REFERENCES health_facilities(uuid));";

    String ANSWER_TABLE = "CREATE TABLE answers(id INTEGER PRIMARY KEY, form_uuid TEXT, mentorship_uuid TEXT, question_uuid TEXT, uuid TEXT UNIQUE, created_at TEXT, text_value TEXT, boolean_value INTEGER, FOREIGN KEY(form_uuid) REFERENCES forms(uuid), FOREIGN KEY(mentorship_uuid) REFERENCES mentorships(uuid), FOREIGN KEY(question_uuid) REFERENCES questions(uuid));";

    String getTableName();

    String getFieldName();

    ContentValues getContentValues(final T entity);

    T getPopulatedEntity(Cursor cursor);

    void create(final T entity);

    void update(final T entity);

    boolean exist(final String name);

    T findByUuid(final String uuid);

    void close();

    void delete(final String whereClause, final String param);
}
