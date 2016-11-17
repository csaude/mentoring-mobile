package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.config.model.HealthFacility;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public class HealthFacilityDAOImpl extends GenericDAOImpl<HealthFacility> implements HealthFacilityDAO {

    private static final String TABLE_NAME = "health_facilities";
    private static final String FIELD_NAME = "health_facility";

    public HealthFacilityDAOImpl(Context context) {
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
    public ContentValues getObjectValues(HealthFacility healthFacility) {
        ContentValues values = new ContentValues();

        values.put("district_id", healthFacility.getDistrict().getId());
        values.put("health_facility", healthFacility.getHealthFacility());

        return values;
    }

    @Override
    public List<HealthFacility> findAll() {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(QUERY.findAll, null);

        List<HealthFacility> healthFacilities = new ArrayList<>();

        while (cursor.moveToNext()) {
            HealthFacility healthFacility = new HealthFacility();
            healthFacility.setId(cursor.getLong(cursor.getColumnIndex("id")));
            healthFacility.setHealthFacility(cursor.getString(cursor.getColumnIndex("health_facility")));

            healthFacilities.add(healthFacility);
        }

        return healthFacilities;
    }
}
