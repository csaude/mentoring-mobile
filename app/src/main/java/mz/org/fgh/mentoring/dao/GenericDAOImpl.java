package mz.org.fgh.mentoring.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import mz.org.fgh.mentoring.model.GenericEntity;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public abstract class GenericDAOImpl<T extends GenericEntity> extends SQLiteOpenHelper implements GenericDAO<T> {

    private static final String name = "mentoringdb";
    private static final int version = 4;

    public GenericDAOImpl(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DISTRICT_TABLE);
        db.execSQL(HEALTH_FACILITY_TABLE);
        db.execSQL(CAREER_TABLE);
        db.execSQL(TUTORED_TABLE);
        db.execSQL(FORM_TABLE);
        db.execSQL(QUESTION_TABLE);
        db.execSQL(FORM_QUESTION_TABLE);
        db.execSQL(MENTORSHIP_TABLE);
        db.execSQL(ANSWER_TABLE);
        db.execSQL(INDICATOR_TABLE);
        db.execSQL(ALTER_FORM_TABLE);
        db.execSQL(ALTER_ANSWER_TABLE);
        db.execSQL(SESSION_TABLE);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_SESSION);
        db.execSQL(CABINET_TABLE);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_CABINET);
        db.execSQL(FORM_TARGETS_TABLE);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_ITERATION_TYPE_COLUMN);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_ITERATION_NUMBER_COLUMN);
        db.execSQL(ALTER_FORM_TABLE_ADD_PATIENT_TARGET);
        db.execSQL(ALTER_FORM_TABLE_ADD_FILE_TARGET);
        db.execSQL(ALTER_FORM_QUESTION_ADD_APPLICABLE);
        db.execSQL(ALTER_TUTORED_TABLE_ADD_LIFE_CYCLE_STATUS);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_TIME_OF_DAY_COLUMN);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_DOOR_COLUMN);
        db.execSQL(ALTER_SESSION_TABLE_ADD_TUTORED_COLUMN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FORM_TARGETS_TABLE);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_ITERATION_TYPE_COLUMN);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_ITERATION_NUMBER_COLUMN);
        db.execSQL(ALTER_FORM_TABLE_ADD_PATIENT_TARGET);
        db.execSQL(ALTER_FORM_TABLE_ADD_FILE_TARGET);
        db.execSQL(ALTER_FORM_QUESTION_ADD_APPLICABLE);
        db.execSQL(ALTER_TUTORED_TABLE_ADD_LIFE_CYCLE_STATUS);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_TIME_OF_DAY_COLUMN);
        db.execSQL(ALTER_MENTORSHIP_TABLE_ADD_DOOR_COLUMN);
        db.execSQL(ALTER_SESSION_TABLE_ADD_TUTORED_COLUMN);
    }

    @Override
    public void create(final T entity) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = getContentValues(entity);

        if (entity.getUuid() == null || entity.getUuid().isEmpty()) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            entity.setUuid(uuid);
        }

        entity.setCreatedAt(new Date());

        values.put("uuid", entity.getUuid());
        values.put("created_at", DateUtil.format(entity.getCreatedAt()));

        database.insert(getTableName(), null, values);
        database.close();
    }

    @Override
    public void update(T entity) {
        SQLiteDatabase database = getWritableDatabase();

        String[] params = new String[]{String.valueOf(entity.getUuid())};
        ContentValues values = getContentValues(entity);

        database.update(getTableName(), values, "uuid = ?", params);
        database.close();
    }

    @Override
    public boolean exist(String name) {

        Cursor cursor = getCursorByParam(name);
        int result = cursor.getCount();
        cursor.close();

        return result > 0;
    }

    private Cursor getCursorByParam(String param) {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + getTableName() + " WHERE " + getFieldName() + " = ? ", new String[]{param});
        cursor.moveToFirst();

        return cursor;
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public T findByUuid(String uuid) {
        return getPopulatedEntity(getCursorByParam(uuid));
    }


    @Override
    public void delete(final String whereClause, final List<String> params) {
        SQLiteDatabase database = getWritableDatabase();

        for (String param : params) {
            database.delete(getTableName(), whereClause, new String[]{param});
        }

        database.close();
    }
}