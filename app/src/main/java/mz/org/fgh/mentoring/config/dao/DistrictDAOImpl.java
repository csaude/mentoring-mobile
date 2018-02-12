package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.District;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;

/**
 * Created by Stélio Moiane on 11/9/16.
 */
public class DistrictDAOImpl extends GenericDAOImpl<District> implements DistrictDAO {

    private static final String TABLE_NAME = "districts";
    private static final String FIELD_NAME = "district";

    public DistrictDAOImpl(Context context) {
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
    public ContentValues getContentValues(final District district) {

        ContentValues values = new ContentValues();

        values.put("province", district.getProvince());
        values.put("district", district.getDistrict());

        return values;
    }

    @Override
    public District getPopulatedEntity(Cursor cursor) {

        District district = new District(cursor.getLong(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("uuid")),
                cursor.getString(cursor.getColumnIndex("province")),
                cursor.getString(cursor.getColumnIndex("district")));

        return district;
    }


    @Override
    public List<District> findAll() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(QUERY.findAll, null);

        List<District> districts = new ArrayList<>();

        while (cursor.moveToNext()) {
            districts.add(getPopulatedEntity(cursor));
        }

        database.close();
        cursor.close();
        return districts;
    }
}
