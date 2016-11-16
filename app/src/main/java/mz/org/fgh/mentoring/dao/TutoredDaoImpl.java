package mz.org.fgh.mentoring.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAO;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Jose Maposse on 14-Nov-16.
 */

public class TutoredDaoImpl extends GenericDAOImpl<Tutored> implements TutoredDao {
    public TutoredDaoImpl(Context context) {
        super(context);
    }

    private static final String TABLE_NAME = "tutoreds";
    private static final String FIELD_NAME = "tutored";


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getFieldName() {
        return FIELD_NAME;
    }

    @Override
    public ContentValues getObjectValues(Tutored tutored) {
        ContentValues values = new ContentValues();

        values.put("name", tutored.getName());
        values.put("surname", tutored.getSurname());
        values.put("phoneNumber", tutored.getPhoneNumber());


        return values;
    }

    @Override
    public List<Tutored> findAll() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(TutoredDao.QUERY.findAll, null);

        List<Tutored> tutoreds= new ArrayList<>();

        while (cursor.moveToNext()) {
            Tutored tutored = new Tutored();
            tutored.setId(cursor.getLong(cursor.getColumnIndex("id")));
            tutored.setName(cursor.getString(cursor.getColumnIndex("name")));
            tutored.setSurname(cursor.getString(cursor.getColumnIndex("surname")));
            tutored.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));

            tutoreds.add(tutored);
        }

        return tutoreds;
    }}
