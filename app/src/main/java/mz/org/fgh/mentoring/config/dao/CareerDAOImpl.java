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

    private static final String TABLE_NAME = "careers";
    private static final String FIELD_NAME = "position";

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
    public ContentValues getObjectValues(Career career) {
        ContentValues values = new ContentValues();

        values.put("career_type", career.getCareerType().toString());
        values.put("position", career.getPosition());

        return values;
    }


    @Override
    public List<Career> findAll() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(CareerDAO.QUERY.findAll, null);

        List<Career> careers = new ArrayList<>();

        while (cursor.moveToNext()) {

            Career career = new Career();
            career.setId(cursor.getLong(cursor.getColumnIndex("id")));
            career.setCareerType(CareerType.valueOf(cursor.getString(cursor.getColumnIndex("career_type"))));
            career.setPosition(cursor.getString(cursor.getColumnIndex("position")));

            careers.add(career);
        }

        cursor.close();

        return careers;
    }

    @Override
    public boolean exist(CareerType carrerType, String position) {
        SQLiteDatabase database = getReadableDatabase();

        String[] params = new String[]{carrerType.toString(), position};
        Cursor cursor = database.rawQuery(QUERY.exist, params);
        int result = cursor.getCount();

        return result > 0;
    }
}
