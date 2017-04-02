package mz.org.fgh.mentoring.process.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by Stélio Moiane on 3/28/17.
 */
public class MentorshipDAOImpl extends GenericDAOImpl<Mentorship> implements MentorshipDAO {

    public MentorshipDAOImpl(Context context) {
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
    public ContentValues getContentValues(Mentorship mentorship) {
        ContentValues values = new ContentValues();

        values.put("form_uuid", mentorship.getForm().getUuid());
        values.put("tutored_uuid", mentorship.getTutored().getUuid());
        values.put("health_facility_uuid", mentorship.getHealthFacility().getUuid());
        values.put("start_date", DateUtil.format(mentorship.getStartDate()));
        values.put("end_date", DateUtil.format(mentorship.getEndDate()));

        return values;
    }

    @Override
    public Mentorship getPopulatedEntity(Cursor cursor) {
        Mentorship mentorship = new Mentorship();

        mentorship.setId(cursor.getLong(cursor.getColumnIndex("id")));
        mentorship.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));

        Form form = new Form();
        form.setUuid(cursor.getString((cursor.getColumnIndex("form_uuid"))));
        form.setName(cursor.getString((cursor.getColumnIndex("form_name"))));
        mentorship.setForm(form);

        Tutored tutored = new Tutored();
        tutored.setUuid(cursor.getString(cursor.getColumnIndex("tutored_uuid")));
        tutored.setName(cursor.getString(cursor.getColumnIndex("tutored_name")));
        tutored.setSurname(cursor.getString(cursor.getColumnIndex("tutored_surname")));
        mentorship.setTutored(tutored);

        HealthFacility healthFacility = new HealthFacility();
        healthFacility.setUuid(cursor.getString(cursor.getColumnIndex("health_facility_uuid")));
        healthFacility.setHealthFacility(cursor.getString(cursor.getColumnIndex("health_facility")));
        mentorship.setHealthFacility(healthFacility);
        mentorship.setStartDate(DateUtil.parse(cursor.getString(cursor.getColumnIndex("start_date"))));
        mentorship.setEndDate(DateUtil.parse(cursor.getString(cursor.getColumnIndex("end_date"))));
        mentorship.setCreatedAt(DateUtil.parse(cursor.getString(cursor.getColumnIndex("created_at"))));

        return mentorship;
    }

    @Override
    public List<Mentorship> findAll() {

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(MentorshipDAO.QUERY.findAll, null);
        List<Mentorship> mentorships = new ArrayList<>();

        while (cursor.moveToNext()) {
            Mentorship mentorship = getPopulatedEntity(cursor);
            mentorships.add(mentorship);
        }

        cursor.close();
        return mentorships;
    }
}
