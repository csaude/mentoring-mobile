package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.CareerType;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public class CareerDAOImpl extends GenericDAOImpl<Career> implements CareerDAO {

    public CareerDAOImpl(Context context) {
        super(context);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

    @Override
    public ContentValues getContentValues(Career career) {
        ContentValues values = new ContentValues();

        values.put("career_type", career.getCareerType().name());
        values.put("position", career.getPosition());

        return values;
    }

    @Override
    public Career getPopulatedEntity(Cursor cursor) {

        Career career = new Career(cursor.getString(cursor.getColumnIndex("uuid")));
        career.setId(cursor.getLong(cursor.getColumnIndex("id")));
        career.setCareerType(CareerType.valueOf(cursor.getString(cursor.getColumnIndex("career_type"))));
        career.setPosition(cursor.getString(cursor.getColumnIndex("position")));

        return career;
    }


    @Override
    public List<Career> findAll() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(CareerDAO.QUERY.findAll, null);

        List<Career> careers = new ArrayList<>();

        while (cursor.moveToNext()) {
            Career career = getPopulatedEntity(cursor);
            careers.add(career);
        }

        database.close();
        cursor.close();
        return careers;
    }


    @Override
    public boolean exist(CareerType carrerType, String position) {
        SQLiteDatabase database = getReadableDatabase();

        String[] params = new String[]{carrerType.toString(), position};
        Cursor cursor = database.rawQuery(QUERY.exist, params);
        int result = cursor.getCount();

        database.close();
        cursor.close();
        return result > 0;
    }

    @Override
    public List<Career> findPositionByCarrerType(CareerType carrerType) {
        SQLiteDatabase database = getReadableDatabase();
        String[] params = new String[]{carrerType.name()};

        Cursor cursor = database.rawQuery(QUERY.findPositionByCarrerType, params);

        List<Career> careers = new ArrayList<>();

        while (cursor.moveToNext()) {
            Career career = getPopulatedEntity(cursor);
            careers.add(career);
        }

        database.close();
        cursor.close();

        return careers;
    }
}
