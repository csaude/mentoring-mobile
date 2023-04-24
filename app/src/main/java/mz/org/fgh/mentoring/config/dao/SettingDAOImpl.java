package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import mz.org.fgh.mentoring.config.model.Setting;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.model.LifeCycleStatus;
import mz.org.fgh.mentoring.util.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SettingDAOImpl extends GenericDAOImpl<Setting> implements SettingDAO {
    public SettingDAOImpl(Context context) {
        super(context);
    }

    @Override
    public String getTableName() {
        return this.TABLE_NAME;
    }

    @Override
    public String getFieldName() {
        return this.FIELD_NAME;
    }

    @Override
    public ContentValues getContentValues(Setting setting) {
        ContentValues values = new ContentValues();

        values.put("designation", setting.getDesignation());
        values.put("value", setting.getValue());
        values.put("uuid", setting.getUuid());
        values.put("life_cycle_status", setting.getLifeCycleStatus().name());

        return values;
    }

    @Override
    public Setting getPopulatedEntity(Cursor cursor) {
        Setting setting = new Setting();

        setting.setId(cursor.getLong(cursor.getColumnIndex("id")));
        setting.setDesignation(cursor.getString(cursor.getColumnIndex("designation")));
        setting.setValue(cursor.getInt(cursor.getColumnIndex("value")));
        setting.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
        setting.setLifeCycleStatus(LifeCycleStatus.valueOf(cursor.getString(cursor.getColumnIndex("life_cycle_status"))));

        return setting;
    }
    @Override
    public Setting findByDesignation(String designation) {
        SQLiteDatabase database = getReadableDatabase();
        List<Setting> settings = new ArrayList<Setting>(0);

        Cursor cursor = database.rawQuery(QUERY.findByDesignation, new String[]{designation});
        while (cursor.moveToNext()) {
            settings.add(getPopulatedEntity(cursor));
        }

        database.close();
        cursor.close();
        if (!Utilities.listHasElements((ArrayList<?>) settings)) return null;

        return settings.get(0);
    }

    @Override
    public List<Setting> findByLifeCycleStatus(LifeCycleStatus lifeCycleStatus) {
        SQLiteDatabase database = getReadableDatabase();
        List<Setting> settings = new ArrayList<Setting>(0);

        Cursor cursor = database.rawQuery(QUERY.findByLifeCycleStatus,  new String[]{ LifeCycleStatus.ACTIVE.name() });
        while (cursor.moveToNext()) {
            settings.add(getPopulatedEntity(cursor));
        }

        database.close();
        cursor.close();
        return settings;
    }
}
