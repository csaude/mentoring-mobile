package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.config.model.QuestionCategory;
import mz.org.fgh.mentoring.config.model.QuestionType;
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
    public ContentValues getContentValues(FormQuestion formQuestion) {

        ContentValues values = new ContentValues();

        values.put("form_uuid", formQuestion.getForm().getUuid());
        values.put("question_uuid", formQuestion.getQuestion().getUuid());
        values.put("sequence", formQuestion.getSequence());

        return values;
    }

    @Override
    public FormQuestion getPopulatedEntity(Cursor cursor) {

        FormQuestion formQuestion = new FormQuestion();
        formQuestion.setId(cursor.getLong(cursor.getColumnIndex("id")));

        Form form = new Form();
        form.setUuid(cursor.getString(cursor.getColumnIndex("form_uuid")));

        formQuestion.setForm(form);

        formQuestion.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));
        formQuestion.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));

        Question question = new Question(cursor.getString(cursor.getColumnIndex("question_uuid")),
                cursor.getString(cursor.getColumnIndex("question")),
                QuestionType.valueOf(cursor.getString(cursor.getColumnIndex("question_type"))),
                QuestionCategory.valueOf(cursor.getString(cursor.getColumnIndex("question_category"))));

        formQuestion.setQuestion(question);

        return formQuestion;
    }

    @Override
    public List<FormQuestion> findByFormUuid(String formUuid) {

        SQLiteDatabase database = getReadableDatabase();
        List<FormQuestion> formQuestions = new ArrayList<>();

        Cursor cursor = database.rawQuery(QUERY.findByFormUuid, new String[]{formUuid});
        while (cursor.moveToNext()) {
            formQuestions.add(getPopulatedEntity(cursor));
        }

        cursor.close();
        database.close();

        return formQuestions;
    }
}
