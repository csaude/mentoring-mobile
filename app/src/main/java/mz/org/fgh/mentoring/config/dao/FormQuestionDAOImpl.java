package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;

import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;

/**
 * Created by Stélio Moiane on 11/17/16.
 */
public class FormQuestionDAOImpl extends GenericDAOImpl<FormQuestion> implements FormQuestionDAO {

    public FormQuestionDAOImpl(Context context) {
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
    public ContentValues getContentValues(FormQuestion entity) {

        ContentValues values = new ContentValues();

        values.put("form_code", entity.getFormCode());
        values.put("question_code", entity.getQuestionCode());

        return values;
    }
}
