package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.config.model.QuestionType;
import mz.org.fgh.mentoring.dao.GenericDAOImpl;
import mz.org.fgh.mentoring.process.model.Mentorship;

/**
 * Created by Stélio Moiane on 3/30/17.
 */
public class AnswerDAOImpl extends GenericDAOImpl<Answer> implements AnswerDAO {

    private Context context;

    public AnswerDAOImpl(Context context) {
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
    public ContentValues getContentValues(Answer answer) {
        ContentValues values = new ContentValues();

        values.put("mentorship_uuid", answer.getMentorship() != null ? answer.getMentorship().getUuid() : null);
        values.put("form_uuid", answer.getForm().getUuid());
        values.put("question_uuid", answer.getQuestion().getUuid());
        values.put("indicator_uuid", answer.getIndicator() != null ? answer.getIndicator().getUuid() : null);

        this.setAnswerContentValue(answer, values);

        return values;
    }

    private void setAnswerContentValue(Answer answer, ContentValues values) {
        switch (answer.getQuestion().getQuestionType()) {
            case TEXT:
                values.put("text_value", answer.getValue());
                break;

            case BOOLEAN:

                Boolean booleanValue = Boolean.valueOf(answer.getValue());
                Integer value = booleanValue ? 1 : 0;
                values.put("boolean_value", value);
                break;

            case NUMERIC:
                values.put("numeric_value", answer.getValue());
                break;
        }
    }

    @Override
    public Answer getPopulatedEntity(Cursor cursor) {

        Mentorship mentorship = new Mentorship();
        mentorship.setUuid(cursor.getString(cursor.getColumnIndex("mentorship_uuid")));

        Form form = new Form();
        form.setUuid(cursor.getString(cursor.getColumnIndex("form_uuid")));

        Question question = new Question(cursor.getString(cursor.getColumnIndex("question_uuid")),
                null,
                QuestionType.valueOf(cursor.getString(cursor.getColumnIndex("question_type"))), null);

        Answer answer = question.getQuestionType().getAnswer();

        answer.setMentorship(mentorship);
        answer.setForm(form);
        answer.setQuestion(question);
        answer.setUuid(cursor.getString(cursor.getColumnIndex("uuid")));

        this.setAnswerValue(answer, cursor);

        return answer;
    }


    private void setAnswerValue(Answer answer, Cursor cursor) {
        switch (answer.getQuestion().getQuestionType()) {

            case TEXT:
                answer.setValue(cursor.getString(cursor.getColumnIndex("text_value")));
                break;

            case BOOLEAN:

                int value = cursor.getInt(cursor.getColumnIndex("boolean_value"));
                Boolean booleanValue = value == 1 ? Boolean.TRUE : Boolean.FALSE;
                answer.setValue(String.valueOf(booleanValue));
                break;

            case NUMERIC:
                answer.setValue(String.valueOf(cursor.getInt(cursor.getColumnIndex("numeric_value"))));
                break;
        }
    }

    @Override
    public List<Answer> findByMentorshipUuid(final String mentorshipUuid) {

        SQLiteDatabase database = getReadableDatabase();
        List<Answer> answers = new ArrayList<>();

        Cursor cursor = database.rawQuery(QUERY.findByMentorshipUuid, new String[]{mentorshipUuid});
        while (cursor.moveToNext()) {
            answers.add(getPopulatedEntity(cursor));
        }

        database.close();
        cursor.close();
        return answers;
    }

    @Override
    public void deleteBySessionUuids(List<String> sessionUuids) {

        SQLiteDatabase database = getWritableDatabase();

        database.rawQuery(QUERY.deleteBySessionUuids, sessionUuids.toArray(new String[sessionUuids.size()]));
        database.close();
    }

    @Override
    public List<Answer> findByIndicatorUuid(String indicatorUuid) {
        SQLiteDatabase database = getReadableDatabase();
        List<Answer> answers = new ArrayList<>();

        Cursor cursor = database.rawQuery(QUERY.findByIndicatorUuid, new String[]{indicatorUuid});
        while (cursor.moveToNext()) {
            answers.add(getPopulatedEntity(cursor));
        }

        database.close();
        cursor.close();
        return answers;
    }

    @Override
    public void deleteByIndicatorUuids(List<String> indicatorsUuids) {
        delete("indicator_uuid IN (?)", indicatorsUuids);
    }
}
