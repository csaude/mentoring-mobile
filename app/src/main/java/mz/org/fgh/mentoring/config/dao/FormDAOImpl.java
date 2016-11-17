package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;

import mz.org.fgh.mentoring.config.model.Form;
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
    public ContentValues getObjectValues(Form entity) {

        ContentValues values = new ContentValues();

        values.put("code", entity.getCode());
        values.put("name", entity.getName());
        values.put("description", entity.getDescription());
        values.put("programmatic_area", entity.getProgrammaticAreaName());

        return values;
    }
}
