package mz.org.fgh.mentoring.process.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.process.model.Indicator;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by steliomo on 10/30/17.
 */

public class IndictorDAOImpl extends GenericDAOImpl<Indicator> implements IndicatorDAO {

    public IndictorDAOImpl(Context context) {
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
    public Indicator getPopulatedEntity(Cursor cursor) {
        Indicator indicator = new Indicator();

        indicator.setId(cursor.getLong(cursor.getColumnIndex("id")));
        indicator.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));

        Tutor tutor = new Tutor();
        tutor.setUuid(cursor.getString(cursor.getColumnIndex("tutor_uuid")));
        indicator.setTutor(tutor);

        Form form = new Form();
        form.setUuid(cursor.getString((cursor.getColumnIndex("form_uuid"))));
        form.setName(cursor.getString((cursor.getColumnIndex("form_name"))));
        form.setTargetPatient(cursor.getInt(cursor.getColumnIndex("target_patient")));
        form.setTargetFile(cursor.getInt(cursor.getColumnIndex("target_file")));
        indicator.setForm(form);

        HealthFacility healthFacility = new HealthFacility();
        healthFacility.setUuid(cursor.getString(cursor.getColumnIndex("health_facility_uuid")));
        healthFacility.setHealthFacility(cursor.getString(cursor.getColumnIndex("health_facility")));
        indicator.setHealthFacility(healthFacility);

        indicator.setCreatedAt(DateUtil.parse(cursor.getString(cursor.getColumnIndex("created_at"))));
        indicator.setPerformedDate(cursor.getString(cursor.getColumnIndex("performed_date")));
        indicator.setReferredMonth(cursor.getString(cursor.getColumnIndex("referred_month")));

        return indicator;
    }

    @Override
    public ContentValues getContentValues(Indicator indicator) {
        ContentValues values = new ContentValues();

        values.put("tutor_uuid", indicator.getTutor().getUuid());
        values.put("form_uuid", indicator.getForm().getUuid());
        values.put("health_facility_uuid", indicator.getHealthFacility().getUuid());
        values.put("performed_date", indicator.getPerformedDate());
        values.put("referred_month", indicator.getReferredMonth());

        return values;
    }

    @Override
    public List<Indicator> findAll() {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(IndicatorDAO.QUERY.findAll, null);
        List<Indicator> indicators = new ArrayList<>();

        while (cursor.moveToNext()) {
            Indicator indicator = getPopulatedEntity(cursor);
            indicators.add(indicator);
        }

        cursor.close();
        return indicators;
    }

    @Override
    public void delete(List<String> uuids) {
        delete(FIELD_NAME + " IN (?)", uuids);
    }
}
