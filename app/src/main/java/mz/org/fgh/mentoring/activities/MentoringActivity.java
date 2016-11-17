package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.config.model.QuestionCategory;
import mz.org.fgh.mentoring.config.model.QuestionType;

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

        questions = Arrays.asList(new Question("NZ001", "COMO TE CHAMAS ?", QuestionType.TEXT, QuestionCategory.ACCURACY),
                new Question("NZ002", "IDADE", QuestionType.TEXT, QuestionCategory.ACCURACY),
                new Question("NZ003", "ENDERECO", QuestionType.TEXT, QuestionCategory.ACCURACY),
                new Question("NZ004", "ESCOLARIDADE", QuestionType.TEXT, QuestionCategory.ACCURACY));


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
