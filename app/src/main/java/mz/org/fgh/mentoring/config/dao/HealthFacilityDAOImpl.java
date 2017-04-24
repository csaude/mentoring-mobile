package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.District;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public class HealthFacilityDAOImpl extends GenericDAOImpl<HealthFacility> implements HealthFacilityDAO {


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
    public ContentValues getContentValues(HealthFacility healthFacility) {
        ContentValues values = new ContentValues();

        values.put("district_uuid", healthFacility.getDistrict().getUuid());
        values.put("health_facility", healthFacility.getHealthFacility());

        return values;
    }

    @Override
    public HealthFacility getPopulatedEntity(Cursor cursor) {

        HealthFacility healthFacility = new HealthFacility();
        District district = new District(cursor.getString(cursor.getColumnIndex("district_uuid")));
        healthFacility.setDistrict(district);

        healthFacility.setId(cursor.getLong(cursor.getColumnIndex("id")));
        healthFacility.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
        healthFacility.setHealthFacility(cursor.getString(cursor.getColumnIndex("health_facility")));

        return healthFacility;
    }

    @Override
    public List<HealthFacility> findAll() {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(QUERY.findAll, null);

        List<HealthFacility> healthFacilities = new ArrayList<>();

        while (cursor.moveToNext()) {
            HealthFacility healthFacility = getPopulatedEntity(cursor);
            healthFacilities.add(healthFacility);
        }

        cursor.close();
        return healthFacilities;
    }
}
