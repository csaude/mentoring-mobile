package mz.org.fgh.mentoring.dao;

import android.content.ContentValues;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public interface GenericDAO<T> {

    String DISTRICT_TABLE = "CREATE TABLE districts(id INTEGER PRIMARY KEY, province TEXT, district TEXT);";

    String HEALTH_FACILITY_TABLE = "CREATE TABLE health_facilities(id INTEGER PRIMARY KEY, health_facility TEXT, district_id INTEGER, FOREIGN KEY(district_id) REFERENCES districts(id));";

    String CAREER_TABLE = "CREATE TABLE careers(id INTEGER PRIMARY KEY, career_type TEXT, position TEXT);";

    String getTableName();

    String getFieldName();

    ContentValues getObjectValues(final T entity);

    Long create(final T entity);

    boolean exist(final String name);

    void close();
}
