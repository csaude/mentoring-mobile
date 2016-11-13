package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;

import mz.org.fgh.mentoring.dao.Career;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;

/**
 * Created by Stélio Moiane on 11/13/16.
 */
public class CareerDAOImpl extends GenericDAOImpl<Career> implements CareerDAO {

    private static final String TABLE_NAME = "programatic_areas";
    private static final String FIELD_NAME = "position";

    public CareerDAOImpl(Context context) {
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
    public ContentValues getObjectValues(Career career) {
        ContentValues values = new ContentValues();

        values.put("career_type", career.getCareerType());
        values.put("position", career.getPosition());

        return values;
    }
}
