package mz.org.fgh.mentoring.config.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.BooleanAnswer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.config.model.QuestionType;
import mz.org.fgh.mentoring.config.model.TextAnswer;
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

        values.put("mentorship_uuid", answer.getMentorship().getUuid());
        values.put("form_uuid", answer.getForm().getUuid());
        values.put("question_uuid", answer.getQuestion().getUuid());

        this.setAnswerContentValue(answer, values);

        return values;
    }

    private void setAnswerContentValue(Answer answer, ContentValues values) {
        switch (answer.getQuestion().getQuestionType()) {
            case TEXT:
                values.put("text_value", ((TextAnswer) answer).getTextValue());
                break;
            case BOOLEAN:
                values.put("boolean_value", ((BooleanAnswer) answer).getBooleanValue());
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
                cursor.getString(cursor.getColumnIndex("code")),
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
                answer.setValue(String.valueOf(cursor.getInt(cursor.getColumnIndex("boolean_value"))));
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

        cursor.close();
        return answers;
    }

    @Override
    public void deleteByMentorshipUuids(List<String> mentorshipsUuids) {
        for (String mentorshipUuid : mentorshipsUuids) {
            delete("mentorship_uuid = ?", mentorshipUuid);
        }
    }
}
