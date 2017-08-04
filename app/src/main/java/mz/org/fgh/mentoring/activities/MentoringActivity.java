package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.config.dao.AnswerDAO;
import mz.org.fgh.mentoring.config.dao.AnswerDAOImpl;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.process.dao.MentorshipDAO;
import mz.org.fgh.mentoring.process.dao.MentorshipDAOImpl;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Month;
import mz.org.fgh.mentoring.util.DateUtil;

public class MentoringActivity extends BaseAuthenticateActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private Bundle bundle = new Bundle();

    private SwipeAdapter adapter;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_mentoring);

        adapter = new SwipeAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void submitProcess() {

        Tutored tutored = (Tutored) bundle.getSerializable("tutored");
        Form form = (Form) bundle.getSerializable("form");

        List<Question> questions = new QuestionDAOImpl(this).findQuestionByForm(form.getUuid());
        HealthFacility healthFacility = (HealthFacility) bundle.getSerializable("healthFacility");

        Mentorship mentorship = new Mentorship();
        mentorship.setStartDate(DateUtil.parse(getIntent().getStringExtra("startDate")));
        mentorship.setEndDate(new Date());
        mentorship.setPerformedDate(DateUtil.parse(bundle.getString("performedDate"), DateUtil.NORMAL_PATTERN));
        mentorship.setReferredMonth((Month) bundle.getSerializable("month"));
        mentorship.setTutored(tutored);
        mentorship.setForm(form);
        mentorship.setHealthFacility(healthFacility);

        MentorshipDAO mentorshipDAO = new MentorshipDAOImpl(this);
        mentorshipDAO.create(mentorship);
        mentorshipDAO.close();

        AnswerDAO answerDAO = new AnswerDAOImpl(this);
        for (Question question : questions) {

            Answer answer = question.getQuestionType().getAnswer();
            answer.setValue(bundle.getString(question.getUuid()));

            answer.setForm(form);
            answer.setMentorship(mentorship);
            answer.setQuestion(question);

            answerDAO.create(answer);
        }

        answerDAO.close();

        startActivity(new Intent(this, ListMentorshipActivity.class));
        finish();
    }

    public SwipeAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {

            case 1:
                Tutored tutored = (Tutored) bundle.getSerializable("tutored");

                if (tutored != null) {
                    return;
                }

                viewPager.setCurrentItem(--position);
                Toast.makeText(this, getString(R.string.tutored_must_be_selected), Toast.LENGTH_SHORT).show();
                break;

            case 2:
                Form form = (Form) bundle.getSerializable("form");

                if (form != null) {
                    return;
                }

                viewPager.setCurrentItem(--position);
                Toast.makeText(this, getString(R.string.form_must_be_selected), Toast.LENGTH_SHORT).show();
                break;

            case 3:
                HealthFacility healthFacility = (HealthFacility) bundle.getSerializable("healthFacility");
                Month month = (Month) bundle.getSerializable("month");
                String performedDate = bundle.getString("performedDate");

                if (healthFacility == null) {
                    Toast.makeText(this, getString(R.string.health_facility_must_be_selected), Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(--position);
                    return;
                }

                if (month == null) {
                    Toast.makeText(this, getString(R.string.referred_month_must_be_selected), Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(--position);
                    return;
                }

                if (performedDate == null) {
                    Toast.makeText(this, getString(R.string.performed_date_must_be_selected), Toast.LENGTH_SHORT).show();
                    viewPager.setCurrentItem(--position);
                    return;
                }

                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
