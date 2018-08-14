package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Cabinet;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by steliomo on 4/12/18.
 */

public class CabinetDAOImpl extends GenericDAOImpl<Cabinet> implements CabinetDAO {


    private Context context;

    public CabinetDAOImpl(Context context) {
        super(context);
        this.context = context;
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
    public ContentValues getContentValues(Cabinet cabinet) {

        ContentValues values = new ContentValues();
        values.put("name", cabinet.getName());

        return values;
    }

    @Override
    public Cabinet getPopulatedEntity(Cursor cursor) {
        Cabinet cabinet = null;

        if (cursor.getCount() == 0) {
            return cabinet;
        }

        cabinet = new Cabinet();

        cabinet.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
        cabinet.setName(cursor.getString(cursor.getColumnIndex("name")));
        cabinet.setCreatedAt(DateUtil.parse(cursor.getString(cursor.getColumnIndex("created_at"))));

        return cabinet;
    }

    @Override
    public List<Cabinet> findAll() {
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(CabinetDAO.QUERY.findAll, null);

        List<Cabinet> cabinets = new ArrayList<>();

        while (cursor.moveToNext()) {
            Cabinet cabinet = getPopulatedEntity(cursor);
            cabinets.add(cabinet);
        }

        database.close();
        cursor.close();
        return cabinets;
    }
}
