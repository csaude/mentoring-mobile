package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Question;

public class MentoringActivity extends FragmentActivity {

    private Bundle bundle;
    private SwipeAdapter adapter;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentoring);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        bundle = new Bundle();

        questions = new QuestionDAOImpl(this).findQuestionByForm("MT00000007");

        adapter = new SwipeAdapter(getSupportFragmentManager(), questions);
        viewPager.setAdapter(adapter);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void submitProcess() {
        List<String> answers = new ArrayList<>();

        for (Question question : questions) {
            bundle.getString(question.getCode());
            answers.add(bundle.getString(question.getCode()));
        }

        Toast.makeText(this, answers + " ", Toast.LENGTH_SHORT).show();
    }
}
