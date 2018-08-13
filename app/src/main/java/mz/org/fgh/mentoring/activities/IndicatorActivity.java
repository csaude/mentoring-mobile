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
import mz.org.fgh.mentoring.adapter.IndicatorSwipeAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.AnswerDAO;
import mz.org.fgh.mentoring.config.dao.FormDAO;
import mz.org.fgh.mentoring.config.dao.QuestionDAO;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormType;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.delegate.FormDelegate;
import mz.org.fgh.mentoring.event.AnswerEvent;
import mz.org.fgh.mentoring.event.FormEvent;
import mz.org.fgh.mentoring.event.HealthFacilityEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.process.dao.IndicatorDAO;
import mz.org.fgh.mentoring.process.model.Indicator;
import mz.org.fgh.mentoring.provider.AnswerProvider;
import mz.org.fgh.mentoring.util.AnswerUtil;
import mz.org.fgh.mentoring.util.DateUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class IndicatorActivity extends BaseAuthenticateActivity implements ViewPager.OnPageChangeListener, AnswerProvider, FormDelegate {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Inject
    EventBus eventBus;

    @Inject
    QuestionDAO questionDAO;

    @Inject
    IndicatorDAO indicatorDAO;

    @Inject
    AnswerDAO answerDAO;

    @Inject
    FormDAO formDAO;

    private IndicatorSwipeAdapter adapter;

    private int currentPosition;

    public static final int DECREMENTER = 1;

    private Indicator indicator;

    private List<Answer> answers;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_mentoring);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);
        eventBus.register(this);

        adapter = new IndicatorSwipeAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        indicator = new Indicator();
        answers = new ArrayList<>();
    }

    private void submitProcess() {

        Tutor tutor = new Tutor();
        tutor.setUuid(application.getAuth().getUser().getUuid());

        indicator.setTutor(tutor);
        indicator.setPerformedDate(DateUtil.format(new Date(), DateUtil.HOURS_PATTERN));

        indicatorDAO.create(indicator);

        for (Answer answer : answers) {

            answer.setForm(indicator.getForm());
            answer.setIndicator(indicator);

            answerDAO.create(answer);
        }

        startActivity(new Intent(this, ListIndicatorsActivity.class));
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        IndicatorSwipeAdapter adapter = (IndicatorSwipeAdapter) viewPager.getAdapter();
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
    public void onFormSelected(FormEvent formEvent) {
        Form form = formEvent.getForm();
        indicator.setForm(form);

        answers = new ArrayList<>();
        adapter.setQuestions(questionDAO.findQuestionByForm(form.getUuid()));
    }

    @Subscribe
    public void onHealthFacilitySelected(HealthFacilityEvent healthFacilityEvent) {
        HealthFacility healthFacility = healthFacilityEvent.getHealthFacility();
        indicator.setHealthFacility(healthFacility);
    }


    @Subscribe
    public void onReferredMonthSelected(MessageEvent<String> referredMonthEvent) {
        String date = referredMonthEvent.getMessage();
        indicator.setReferredMonth(date);
    }

    @Subscribe
    public void onAnswerSelected(AnswerEvent answerEvent) {
        Answer answer = answerEvent.getAnswer();
        AnswerUtil.addAnswer(answers, answer);
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

    @Override
    public List<Form> getForms() {
        return formDAO.findByFormType(FormType.INDICATORS.name());
    }
}
