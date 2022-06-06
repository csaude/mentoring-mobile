package mz.org.fgh.mentoring.process.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.process.model.SessionStatus;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by steliomo on 2/6/18.
 */

public class SessionDAOImpl extends GenericDAOImpl<Session> implements SessionDAO {

    public SessionDAOImpl(Context context) {
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
    public ContentValues getContentValues(Session session) {

        ContentValues values = new ContentValues();
        values.put("form_uuid", session.getForm().getUuid());
        values.put("tutored_uuid", session.getTutored().getUuid());
        values.put("health_facility_uuid", session.getHealthFacility().getUuid());
        values.put("start_date", session.getStartDate());
        values.put("end_date", session.getEndDate());
        values.put("performed_date", session.getPerformedDate());
        values.put("status", session.getStatus().name());
        values.put("reason", session.getReason());

        return values;
    }

    @Override
    public Session getPopulatedEntity(Cursor cursor) {
        Session session = new Session();

        session.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));

        Form form = new Form();
        form.setUuid(cursor.getString((cursor.getColumnIndex("form_uuid"))));
        form.setName(cursor.getString((cursor.getColumnIndex("form_name"))));
        form.setTargetPatient(cursor.getInt(cursor.getColumnIndex("target_patient")));
        form.setTargetFile(cursor.getInt(cursor.getColumnIndex("target_file")));
        session.setForm(form);

        HealthFacility healthFacility = new HealthFacility();
        healthFacility.setUuid(cursor.getString(cursor.getColumnIndex("health_facility_uuid")));
        healthFacility.setHealthFacility(cursor.getString(cursor.getColumnIndex("health_facility")));
        session.setHealthFacility(healthFacility);

        session.setStartDate(DateUtil.parse(cursor.getString(cursor.getColumnIndex("start_date"))));
        session.setEndDate(DateUtil.parse(cursor.getString(cursor.getColumnIndex("end_date"))));
        session.setCreatedAt(DateUtil.parse(cursor.getString(cursor.getColumnIndex("created_at"))));
        session.setPerformedDate(DateUtil.parse(cursor.getString(cursor.getColumnIndex("performed_date")), DateUtil.NORMAL_PATTERN));

        session.setStatus(SessionStatus.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
        session.setReason(cursor.getString(cursor.getColumnIndex("reason")));

        Tutored tutored = new Tutored();
        tutored.setUuid(cursor.getString((cursor.getColumnIndex("tutored_uuid"))));
        tutored.setName(cursor.getString((cursor.getColumnIndex("tutored_name"))));
        session.setTutored(tutored);

        return session;
    }

    @Override
    public List<Session> findAll() {

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(QUERY.findAll, null);
        List<Session> sessions = new ArrayList<>();

        while (cursor.moveToNext()) {
            Session session = getPopulatedEntity(cursor);
            sessions.add(session);
        }

        database.close();
        cursor.close();

        return sessions;
    }

    @Override
    public List<Session> findAllToSync() {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(QUERY.findAllToSync, null);
        List<Session> sessions = new ArrayList<>();

        while (cursor.moveToNext()) {
            Session session = getPopulatedEntity(cursor);
            sessions.add(session);
        }

        database.close();
        cursor.close();

        return sessions;
    }

    @Override
    public List<Session> findSessionsByUuids(String uuids) {
        SQLiteDatabase database = getReadableDatabase();

        String findToSyncByUuid = "SELECT s.id, s.uuid, s.form_uuid, s.health_facility_uuid, f.name as form_name, s.tutored_uuid, t.name || ' ' || t.surname as tutored_name, " +
                "hf.health_facility as health_facility, s.start_date, s.end_date, s.performed_date, s.status, s.reason, s.created_at, f.target_patient, f.target_file " +
                "FROM " + TABLE_NAME + " s " +
                "INNER JOIN forms f ON s.form_uuid = f.uuid " +
                "INNER JOIN tutoreds t ON s.tutored_uuid = t.uuid " +
                "INNER JOIN health_facilities hf ON s.health_facility_uuid = hf.uuid AND s.uuid IN ("+uuids+") ORDER BY s.id ASC LIMIT 5";

        Cursor cursor = database.rawQuery(findToSyncByUuid, null);
        List<Session> sessions = new ArrayList<>();

        while (cursor.moveToNext()) {
            Session session = getPopulatedEntity(cursor);
            sessions.add(session);
        }

        database.close();
        cursor.close();

        return sessions;
    }

    @Override
    public void deleteByUuids(List<String> uuids) {
        delete("uuid IN (?)", uuids);
    }
}
