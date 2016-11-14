package mz.org.fgh.mentoring.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public abstract class GenericDAOImpl<T> extends SQLiteOpenHelper implements GenericDAO<T> {

    private static final String name = "mentoringdb";
    private static final int version = 1;

    public GenericDAOImpl(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DISTRICT_TABLE);
        db.execSQL(HEALTH_FACILITY_TABLE);
        db.execSQL(CAREER_TABLE);
        db.execSQL(TUTORED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public Long create(final T entity) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = getObjectValues(entity);

        return database.insert(getTableName(), null, values);
    }

    @Override
    public boolean exist(String name) {

        SQLiteDatabase database = getReadableDatabase();

        String[] params = new String[]{name};
        Cursor cursor = database.rawQuery("SELECT * FROM " + getTableName() + " where " + getFieldName() + " = ? ", params);
        int result = cursor.getCount();

        return result > 0;
    }

    @Override
    public void close() {
        super.close();
    }
}
