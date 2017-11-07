package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.AnswerDAO;
import mz.org.fgh.mentoring.config.dao.AnswerDAOImpl;
import mz.org.fgh.mentoring.config.dao.QuestionDAO;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.event.FormEvent;
import mz.org.fgh.mentoring.event.HealthFacilityEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.event.MonthEvent;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.event.TutoredEvent;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.process.dao.MentorshipDAO;
import mz.org.fgh.mentoring.process.dao.MentorshipDAOImpl;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Month;
import mz.org.fgh.mentoring.util.DateUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class MentoringActivity extends BaseAuthenticateActivity implements ViewPager.OnPageChangeListener, AnswerActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    EventBus eventBus;

    @Inject
    QuestionDAO questionDAO;

    @Inject
    MentorshipDAO mentorshipDAO;

    @Inject
    AnswerDAO answerDAO;

    private SwipeAdapter adapter;

    private int currentPosition;

    public static final int DECREMENTER = 1;

    private Mentorship mentorship;

    private List<Answer> answers;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_mentoring);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);
        eventBus.register(this);

        adapter = new SwipeAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        mentorship = new Mentorship();
        answers = new ArrayList<>();
    }

    private void submitProcess() {

        mentorship.setStartDate(DateUtil.parse(getIntent().getStringExtra("startDate")));
        mentorship.setEndDate(new Date());

        mentorshipDAO.create(mentorship);

        for (Answer answer : answers) {

            answer.setForm(mentorship.getForm());
            answer.setMentorship(mentorship);

            answerDAO.create(answer);
        }

        startActivity(new Intent(this, ListMentorshipActivity.class));
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        SwipeAdapter adapter = (SwipeAdapter) viewPager.getAdapter();
        setCurrentPosition(position);

        Object item = adapter.instantiateItem(viewPager, getValidatorPosition());

        if (item instanceof FragmentValidator) {
            FragmentValidator fragment = (FragmentValidator) item;
            fragment.validate(viewPager, getValidatorPosition());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setCurrentPosition(int position) {

        if (position <= currentPosition) {
            return;
        }

        currentPosition = position;
    }

    public int getValidatorPosition() {
        return currentPosition - DECREMENTER;
    }

    @Subscribe
    public void onTutoredSelected(TutoredEvent tutoredEvent) {
        Tutored tutored = tutoredEvent.getTutored();
        mentorship.setTutored(tutored);
    }

    @Subscribe
    public void onFormSelected(FormEvent formEvent) {
        Form form = formEvent.getForm();
        mentorship.setForm(form);

        adapter.setQuestions(questionDAO.findQuestionByForm(form.getUuid()));
    }

    @Subscribe
    public void onHealthFacilitySelected(HealthFacilityEvent healthFacilityEvent) {
        HealthFacility healthFacility = healthFacilityEvent.getHealthFacility();
        mentorship.setHealthFacility(healthFacility);
    }

    @Subscribe
    public void onMonthSelected(MonthEvent monthEvent) {
        Month month = monthEvent.getMonth();
        mentorship.setReferredMonth(month);
    }

    @Subscribe
    public void onDatePerformedSelected(MessageEvent<String> datePerformedEvent) {
        String date = datePerformedEvent.getMessage();
        mentorship.setPerformedDate(DateUtil.parse(date, DateUtil.NORMAL_PATTERN));
    }

    @Subscribe
    public void onAnswerSelected(AnswerEvent answerEvent) {
        Answer answer = answerEvent.getAnswer();
        answers.add(answer);
    }

    @Subscribe
    public void onProcessSelected(ProcessEvent processEvent) {
        submitProcess();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    public List<Answer> getAnswers() {
        return answers;
    }
}
