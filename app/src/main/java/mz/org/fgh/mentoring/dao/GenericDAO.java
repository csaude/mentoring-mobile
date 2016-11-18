package mz.org.fgh.mentoring.dao;

import android.content.ContentValues;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public interface GenericDAO<T extends GenericEntity> {

    String DISTRICT_TABLE = "CREATE TABLE districts(id INTEGER PRIMARY KEY, province TEXT, district TEXT);";

    String HEALTH_FACILITY_TABLE = "CREATE TABLE health_facilities(id INTEGER PRIMARY KEY, health_facility TEXT, district_id INTEGER, FOREIGN KEY(district_id) REFERENCES districts(id));";

    String CAREER_TABLE = "CREATE TABLE careers(id INTEGER PRIMARY KEY, career_type TEXT, position TEXT);";

    String TUTORED_TABLE = "CREATE TABLE tutoreds(id INTEGER PRIMARY KEY, code TEXT, name TEXT, surname TEXT, phoneNumber TEXT, carrer_id INTEGER, uuid TEXT, FOREIGN KEY(carrer_id) REFERENCES careers(id));";

    String FORM_TABLE = "CREATE TABLE forms(id INTEGER PRIMARY KEY, code TEXT, name TEXT, description TEXT, programmatic_area TEXT, version INTEGER, CONSTRAINT code_unique UNIQUE (code));";

    String QUESTION_TABLE = "CREATE TABLE questions(id INTEGER PRIMARY KEY, code TEXT, question TEXT, question_type TEXT, question_category TEXT, CONSTRAINT code_unique UNIQUE (code));";

    String FORM_QUESTION_TABLE = "CREATE TABLE form_questions(id INTEGER PRIMARY KEY, form_code TEXT, question_code TEXT, FOREIGN KEY(form_code) REFERENCES forms(code), FOREIGN KEY(question_code) REFERENCES questions(code));";

    String getTableName();

    String getFieldName();

    ContentValues getContentValues(final T entity);

    void create(final T entity);

    void update(final T entity);

    boolean exist(final String name);

    void close();
}
