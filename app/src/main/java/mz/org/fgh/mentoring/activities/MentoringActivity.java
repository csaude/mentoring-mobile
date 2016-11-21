package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.model.Tutored;

public class MentoringActivity extends BaseAuthenticateActivity {

    private Bundle bundle = new Bundle();
    private SwipeAdapter adapter;
    private List<Question> questions;


    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_mentoring);

        questions = new QuestionDAOImpl(this).findQuestionByForm("MT00000007");

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        adapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public Bundle getBundle() {
        return this.bundle;
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

        Tutored tutored = (Tutored) bundle.getSerializable("tutored");
        Form form = (Form) bundle.getSerializable("form");
        HealthFacility healthFacility = (HealthFacility) bundle.getSerializable("healthFacility");


        Toast.makeText(this, answers + " -- turando: " + tutored.getName() + "-- formulario: " + form.getName() + "-- HF " + healthFacility.getHealthFacility(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mentoring_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mentoring_menu_sync:
                Toast.makeText(this, "Processos enviados....", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public SwipeAdapter getAdapter() {
        return adapter;
    }
}
