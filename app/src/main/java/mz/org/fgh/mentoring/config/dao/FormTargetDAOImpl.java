package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormTarget;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;

public class FormTargetDAOImpl extends GenericDAOImpl<FormTarget> implements FormTargetDAO {

    public FormTargetDAOImpl(Context context) {
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
    public ContentValues getContentValues(FormTarget entity) {

        ContentValues values = new ContentValues();

        values.put("form_uuid", entity.getForm().getUuid());
        values.put("career_uuid", entity.getCareer().getUuid());
        values.put("target", entity.getTarget());

        return values;
    }

    @Override
    public FormTarget getPopulatedEntity(Cursor cursor) {

        if (cursor.getCount() == 0) {
            return null;
        }

        FormTarget formTarget = new FormTarget();

        formTarget.setId(cursor.getLong(cursor.getColumnIndex("id")));
        formTarget.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
        formTarget.setTarget(cursor.getInt(cursor.getColumnIndex("target")));

        Form form = new Form();
        form.setUuid(cursor.getString(cursor.getColumnIndex("form_uuid")));
        formTarget.setForm(form);


        Career career = new Career();
        career.setUuid(cursor.getString(cursor.getColumnIndex("career_uuid")));
        formTarget.setCareer(career);

        return formTarget;
    }

    @Override
    public FormTarget findByFormUuid(String formUuid) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(QUERY.findByFormUuid, new String[]{formUuid});
        cursor.moveToFirst();

        FormTarget formTarget = getPopulatedEntity(cursor);

        cursor.close();
        db.close();

        return formTarget;
    }
}
