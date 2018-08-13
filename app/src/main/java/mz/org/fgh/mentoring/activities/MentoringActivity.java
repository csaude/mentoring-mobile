package mz.org.fgh.mentoring.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.FormDAO;
import mz.org.fgh.mentoring.config.dao.FormQuestionDAO;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.FormType;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.delegate.FormDelegate;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.event.CabinetEvent;
import mz.org.fgh.mentoring.event.FormEvent;
import mz.org.fgh.mentoring.event.HealthFacilityEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.event.TimeEvent;
import mz.org.fgh.mentoring.event.TutoredEvent;
import mz.org.fgh.mentoring.fragment.ConfirmationFragment;
import mz.org.fgh.mentoring.fragment.SaveFragment;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.provider.AnswerProvider;
import mz.org.fgh.mentoring.provider.SessionProvider;
import mz.org.fgh.mentoring.service.SessionService;
import mz.org.fgh.mentoring.util.DateUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class MentoringActivity extends BaseAuthenticateActivity implements ViewPager.OnPageChangeListener, AnswerProvider, SessionProvider, View.OnClickListener, FormDelegate {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    EventBus eventBus;

    @Inject
    FormQuestionDAO formQuestionDAO;

    @Inject
    SessionService sessionService;

    @Inject
    FormDAO formDAO;

    private SwipeAdapter adapter;

    private int currentPosition;

    public static final int DECREMENTER = 1;

    private Mentorship mentorship;

    private Session session;

    private Tutored tutored;

    private Form form;

    @SuppressLint("ResourceType")
    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_mentoring);

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(this);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);
        eventBus.register(this);

        adapter = new SwipeAdapter(getSupportFragmentManager(), this);

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
        this.mentorship.setCabinet(session.getCabinet());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        if (currentPosition > position) {
            currentPosition = position;
            return;
        }

        if (currentPosition < position) {
            Fragment item = (Fragment) adapter.instantiateItem(viewPager, this.currentPosition);

            if (item instanceof FragmentValidator) {
                FragmentValidator fragment = (FragmentValidator) item;

                if (item.isAdded()) {
                    fragment.validate(viewPager, this.currentPosition);

                    if (fragment.isValid()) {
                        this.currentPosition = position;
                    }
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Subscribe
    public void onTutoredSelected(TutoredEvent tutoredEvent) {
        this.tutored = tutoredEvent.getTutored();
    }

    @Subscribe
    public void onFormSelected(FormEvent formEvent) {

        if (this.form != null && this.form.getUuid().equals(formEvent.getForm().getUuid())) {
            return;
        }

        this.form = formEvent.getForm();
        session.setForm(this.form);

        List<FormQuestion> formQuestions = formQuestionDAO.findByFormUuid(this.form.getUuid());

        for (FormQuestion formQuestion : formQuestions) {
            this.form.addFormQuestion(formQuestion);
        }

        adapter.setForm(this.form);
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

        if (isLastQuestion()) {
            adapter.addFragment(viewPager.getCurrentItem(), new ConfirmationFragment());
        }
    }

    @Subscribe
    public void onCabinetSelected(CabinetEvent cabinetEvent) {
        this.session.setCabinet(cabinetEvent.getCabinet());
    }

    @Subscribe
    public void onProcessSelected(ProcessEvent processEvent) {

        switch (processEvent.getEventType()) {

            case SUBMIT:
                submitProcess();
                break;

            case CONTINUE:
                populateMentorship();

                this.session.addMentorship(mentorship);
                this.mentorship = new Mentorship();

                adapter = new SwipeAdapter(getSupportFragmentManager(), this);
                this.form.clearAnswers();
                adapter.setForm(this.form);
                adapter.setSession(session);
                viewPager.setAdapter(adapter);

                currentPosition = 3; //go to the first question...
                viewPager.setCurrentItem(currentPosition, true);
                break;

            case TERMINATE:
                this.session.setReason(processEvent.getReason());
                adapter.setSession(session);
                adapter.addFragment(viewPager.getCurrentItem(), new SaveFragment());
                viewPager.setCurrentItem(viewPager.getCurrentItem() + DECREMENTER, true);
                break;
        }
    }

    @Subscribe
    public void onTimePicker(TimeEvent timeEvent) {

        if (session.getPerformedDate() == null) {
            return;
        }

        Date performedDate = DateUtil.parse(session.getPerformedDate(), DateUtil.NORMAL_PATTERN);
        Calendar instance = Calendar.getInstance();
        instance.setTime(performedDate);

        if (timeEvent.getStartHour() != 0) {
            instance.set(Calendar.HOUR_OF_DAY, timeEvent.getStartHour());
            instance.set(Calendar.MINUTE, timeEvent.getStartMinute());
            instance.set(Calendar.SECOND, 0);

            session.setStartDate(instance.getTime());
            return;
        }

        if (timeEvent.getEndHour() != 0) {
            instance.set(Calendar.HOUR_OF_DAY, timeEvent.getEndHour());
            instance.set(Calendar.MINUTE, timeEvent.getEndMinute());
            instance.set(Calendar.SECOND, 0);

            session.setEndDate(instance.getTime());
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public List<Answer> getAnswers() {
        return this.mentorship.getAnswers();
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    private boolean isLastQuestion() {
        return adapter.getQuestionsSize() == viewPager.getCurrentItem() + DECREMENTER;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, ListMentorshipActivity.class));
        finish();
    }

    @Override
    public List<Form> getForms() {
        return formDAO.findByFormType(FormType.MENTORING);
    }
}
