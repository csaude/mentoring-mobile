package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Jose Maposse on 14-Nov-16.
 */

public class TutoredDAOImpl extends GenericDAOImpl<Tutored> implements TutoredDAO {
    public TutoredDAOImpl(Context context) {
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
    public ContentValues getContentValues(Tutored tutored) {
        ContentValues values = new ContentValues();

        values.put("code", tutored.getCode());
        values.put("name", tutored.getName());
        values.put("surname", tutored.getSurname());
        values.put("phoneNumber", tutored.getPhoneNumber());
        values.put("carrer_id", tutored.getCarrerId());

        return values;
    }

    @Override
    public Tutored getPopulatedEntity(Cursor cursor) {
        Tutored tutored = new Tutored();

        tutored.setId(cursor.getLong(cursor.getColumnIndex("id")));
        tutored.setCode(cursor.getString(cursor.getColumnIndex("code")));
        tutored.setName(cursor.getString(cursor.getColumnIndex("name")));
        tutored.setSurname(cursor.getString(cursor.getColumnIndex("surname")));
        tutored.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
        tutored.setCarrerId(cursor.getLong(cursor.getColumnIndex("carrer_id")));
        tutored.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));

        Career career = new Career();
        career.setId(tutored.getCarrerId());
        tutored.setCareer(career);

        return tutored;
    }

    @Override
    public List<Tutored> findAll() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(TutoredDAO.QUERY.findAll, null);

        List<Tutored> tutoreds = new ArrayList<>();

        while (cursor.moveToNext()) {
            Tutored tutored = getPopulatedEntity(cursor);
            tutoreds.add(tutored);
        }

        cursor.close();
        return tutoreds;
    }

    @Override
    public List<Tutored> findAllWithNoCode() {

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(QUERY.findAllWithNoCode, null);

        List<Tutored> tutoreds = new ArrayList<>();

        while (cursor.moveToNext()) {
            Tutored tutored = getPopulatedEntity(cursor);
            tutoreds.add(tutored);
        }

        cursor.close();
        return tutoreds;
    }
}
