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
        form.setTarget(cursor.getInt(cursor.getColumnIndex("target")));
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
    public void deleteByUuids(List<String> uuids) {
        delete("uuid IN (?)", uuids);
    }
}
