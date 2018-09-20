package mz.org.fgh.mentoring.process.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Cabinet;
import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.process.model.IterationType;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;
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
        values.put("start_date", mentorship.getStartDate());
        values.put("end_date", mentorship.getEndDate());
        values.put("performed_date", mentorship.getPerformedDate());
        values.put("session_uuid", mentorship.getSession().getUuid());
        values.put("cabinet_uuid", mentorship.getCabinet() != null ? mentorship.getCabinet().getUuid() : null);
        values.put("iteration_type", mentorship.getIterationType().name());
        values.put("iteration_number", mentorship.getIterationNumber());

        return values;
    }

    @Override
    public Mentorship getPopulatedEntity(Cursor cursor) {
        Mentorship mentorship = new Mentorship();

        mentorship.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));

        Form form = new Form();
        form.setUuid(cursor.getString((cursor.getColumnIndex("form_uuid"))));
        form.setName(cursor.getString((cursor.getColumnIndex("form_name"))));
        form.setTargetPatient(cursor.getInt(cursor.getColumnIndex("target_patient")));
        form.setTargetFile(cursor.getInt(cursor.getColumnIndex("target_file")));
        mentorship.setForm(form);

        Tutored tutored = new Tutored();
        tutored.setUuid(cursor.getString(cursor.getColumnIndex("tutored_uuid")));
        tutored.setName(cursor.getString(cursor.getColumnIndex("tutored_name")));
        tutored.setSurname(cursor.getString(cursor.getColumnIndex("tutored_surname")));
        tutored.setPhoneNumber(cursor.getString(cursor.getColumnIndex("tutored_phone_number")));

        Career career = new Career();
        career.setUuid(cursor.getString((cursor.getColumnIndex("tutored_career_uuid"))));
        tutored.setCareer(career);

        mentorship.setTutored(tutored);

        HealthFacility healthFacility = new HealthFacility();
        healthFacility.setUuid(cursor.getString(cursor.getColumnIndex("health_facility_uuid")));
        healthFacility.setHealthFacility(cursor.getString(cursor.getColumnIndex("health_facility")));

        Cabinet cabinet = new Cabinet();
        cabinet.setUuid(cursor.getString(cursor.getColumnIndex("cabinet_uuid")));

        mentorship.setHealthFacility(healthFacility);
        mentorship.setStartDate(DateUtil.parse(cursor.getString(cursor.getColumnIndex("start_date"))));
        mentorship.setEndDate(DateUtil.parse(cursor.getString(cursor.getColumnIndex("end_date"))));
        mentorship.setCreatedAt(DateUtil.parse(cursor.getString(cursor.getColumnIndex("created_at"))));
        mentorship.setPerformedDate(DateUtil.parse(cursor.getString(cursor.getColumnIndex("performed_date")), DateUtil.NORMAL_PATTERN));
        mentorship.setIterationType(IterationType.valueOf(cursor.getString(cursor.getColumnIndex("iteration_type"))));
        mentorship.setIterationNumber(cursor.getInt(cursor.getColumnIndex("iteration_number")));
        mentorship.setCabinet(cabinet);

        return mentorship;
    }

    @Override
    public List<Mentorship> findAll() {

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(QUERY.findAll, null);
        List<Mentorship> mentorships = new ArrayList<>();

        while (cursor.moveToNext()) {
            Mentorship mentorship = getPopulatedEntity(cursor);
            mentorships.add(mentorship);
        }

        database.close();
        cursor.close();
        return mentorships;
    }

    @Override
    public void deleteBySessionUuids(final List<String> sessionUuids) {
        delete("session_uuid IN (?)", sessionUuids);
    }

    @Override
    public List<Mentorship> findBySession(Session session) {
        SQLiteDatabase database = getReadableDatabase();

        List<Mentorship> mentorships = new ArrayList<>();
        Cursor cursor = database.rawQuery(QUERY.findBySession, new String[]{session.getUuid()});

        while (cursor.moveToNext()) {
            Mentorship mentorship = getPopulatedEntity(cursor);
            mentorships.add(mentorship);
        }

        return mentorships;
    }
}
