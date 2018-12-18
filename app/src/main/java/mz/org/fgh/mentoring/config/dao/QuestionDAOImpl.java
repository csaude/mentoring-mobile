package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.config.model.QuestionCategory;
import mz.org.fgh.mentoring.config.model.QuestionType;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;

/**
 * Created by Stélio Moiane on 11/17/16.
 */
public class QuestionDAOImpl extends GenericDAOImpl<Question> implements QuestionDAO {

    public QuestionDAOImpl(Context context) {
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
    public ContentValues getContentValues(Question entity) {

        ContentValues values = new ContentValues();

        values.put("question", entity.getQuestion());
        values.put("question_type", entity.getQuestionType().name());

        // I don't understand why some questions do not have category associated with them
        // Maybe Stelio can answer this question or may be it bad data (No Clue)
        if(entity.getQuestionCategory() != null) {
            values.put("question_category", entity.getQuestionCategory().name());
        }

        return values;
    }

    @Override
    public Question getPopulatedEntity(Cursor cursor) {

        Question question = new Question(cursor.getString(cursor.getColumnIndex("uuid")),
                cursor.getString(cursor.getColumnIndex("question")),
                QuestionType.valueOf(cursor.getString(cursor.getColumnIndex("question_type"))),
                new QuestionCategory(cursor.getString(cursor.getColumnIndex("question_category"))));

        question.setId(cursor.getLong(cursor.getColumnIndex("id")));

        return question;
    }

    @Override
    public List<Question> findQuestionByForm(String formCode) {
        List<Question> questions = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        String[] params = {formCode};

        Cursor cursor = database.rawQuery(QUERY.findQuestionByForm, params);

        while (cursor.moveToNext()) {
            Question question = getPopulatedEntity(cursor);
            questions.add(question);
        }

        database.close();
        cursor.close();
        return questions;
    }
}
