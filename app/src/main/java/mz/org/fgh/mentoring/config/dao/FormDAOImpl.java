package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.ProgrammaticArea;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class FormDAOImpl extends GenericDAOImpl<Form> implements FormDAO {

    public FormDAOImpl(Context context) {
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
    public ContentValues getContentValues(Form entity) {

        ContentValues values = new ContentValues();

        values.put("code", entity.getCode());
        values.put("name", entity.getName());
        values.put("programmatic_area_uuid", entity.getProgrammaticArea().getUuid());
        values.put("version", entity.getVersion());

        return values;
    }

    @Override
    public Form getPopulatedEntity(Cursor cursor) {

        ProgrammaticArea programmaticArea = new ProgrammaticArea(cursor.getString(cursor.getColumnIndex("programmatic_area_uuid")));

        Form form = new Form(cursor.getString(cursor.getColumnIndex("uuid")),
                cursor.getString(cursor.getColumnIndex("code")),
                cursor.getString(cursor.getColumnIndex("name")),
                programmaticArea,
                cursor.getString(cursor.getColumnIndex("version")));

        form.setId(cursor.getLong(cursor.getColumnIndex("id")));

        return form;
    }

    @Override
    public List<Form> findAll() {

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(QUERY.findAll, null);
        List<Form> forms = new ArrayList<>();

        while (cursor.moveToNext()) {
            Form form = getPopulatedEntity(cursor);
            forms.add(form);
        }

        cursor.close();
        return forms;
    }
}
