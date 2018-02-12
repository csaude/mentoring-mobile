package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.QuestionDAO;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.event.FormEvent;
import mz.org.fgh.mentoring.event.HealthFacilityEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.event.TutoredEvent;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.provider.AnswerProvider;
import mz.org.fgh.mentoring.provider.SessionProvider;
import mz.org.fgh.mentoring.service.SessionService;
import mz.org.fgh.mentoring.util.DateUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;
import mz.org.fgh.mentoring.validator.IterationFragment;

public class MentoringActivity extends BaseAuthenticateActivity implements ViewPager.OnPageChangeListener, AnswerProvider, SessionProvider {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    EventBus eventBus;

    @Inject
    QuestionDAO questionDAO;

    @Inject
    SessionService sessionService;

    private SwipeAdapter adapter;

    private int currentPosition;

    public static final int DECREMENTER = 1;

    private Mentorship mentorship;

    private Session session;

    private List<Question> questions;

    private Tutored tutored;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_mentoring);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);
        eventBus.register(this);

        adapter = new SwipeAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        session = new Session();
        mentorship = new Mentorship();
    }

    private void submitProcess() {
        populateMentorship();

        this.session.setEndDate(new Date());
        this.session.addMentorship(mentorship);
        this.session.setStatus();

        sessionService.createSession(session);

        startActivity(new Intent(this, ListMentorshipActivity.class));
        finish();
    }

    private void populateMentorship() {
        this.mentorship.setEndDate(new Date());
        this.mentorship.setTutored(tutored);
        this.mentorship.setHealthFacility(session.getHealthFacility());
        this.mentorship.setPerformedDate(DateUtil.parse(session.getPerformedDate(), DateUtil.NORMAL_PATTERN));
        this.mentorship.setForm(session.getForm());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentPosition(position);

        Object itemInstance = adapter.instantiateItem(viewPager, position);

        if (itemInstance instanceof IterationFragment) {
            IterationFragment iterationFragment = (IterationFragment) itemInstance;
            iterationFragment.updateButtons();
            iterationFragment.updateIterations();
        }

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
        this.tutored = tutoredEvent.getTutored();
    }

    @Subscribe
    public void onFormSelected(FormEvent formEvent) {
        session.setForm(formEvent.getForm());
        questions = questionDAO.findQuestionByForm(session.getForm().getUuid());
        adapter.setQuestions(questions);
    }

    @Subscribe
    public void onHealthFacilitySelected(HealthFacilityEvent healthFacilityEvent) {
        HealthFacility healthFacility = healthFacilityEvent.getHealthFacility();
        session.setHealthFacility(healthFacility);
    }

    @Subscribe
    public void onDatePerformedSelected(MessageEvent<String> datePerformedEvent) {
        String date = datePerformedEvent.getMessage();
        this.session.setPerformedDate(DateUtil.parse(date, DateUtil.NORMAL_PATTERN));
    }

    @Subscribe
    public void onAnswerSelected(AnswerEvent answerEvent) {
        Answer answer = answerEvent.getAnswer();
        this.mentorship.addAnswer(answer);
    }

    @Subscribe
    public void onProcessSelected(ProcessEvent processEvent) {

        switch (processEvent.getEventType()) {

            case SUBMIT:
                this.session.setReason(processEvent.getReason());
                submitProcess();
                break;

            case CONTINUE:
                populateMentorship();

                this.session.addMentorship(mentorship);
                this.mentorship = new Mentorship();

                adapter = new SwipeAdapter(getSupportFragmentManager());
                adapter.setQuestions(this.questions);
                viewPager.setAdapter(adapter);

                currentPosition = 3; //go to the first question...
                viewPager.setCurrentItem(currentPosition, true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Override
    public List<Answer> getAnswers() {
        return this.mentorship.getAnswers();
    }

    @Override
    public Session getSession() {
        return this.session;
    }
}
