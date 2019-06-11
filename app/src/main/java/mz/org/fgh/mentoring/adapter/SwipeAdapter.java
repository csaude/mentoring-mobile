package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormType;
import mz.org.fgh.mentoring.config.model.QuestionCategory;
import mz.org.fgh.mentoring.fragment.ConfirmationFragment;
import mz.org.fgh.mentoring.fragment.FormsFragment;
import mz.org.fgh.mentoring.fragment.HealthFacilityFragment;
import mz.org.fgh.mentoring.fragment.IterationTypeFragment;
import mz.org.fgh.mentoring.fragment.QuestionFragment;
import mz.org.fgh.mentoring.fragment.SaveFragment;
import mz.org.fgh.mentoring.fragment.TutoredFragment;
import mz.org.fgh.mentoring.process.model.IterationType;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by Stélio Moiane on 11/14/16.
 */
public class SwipeAdapter extends FragmentStatePagerAdapter {

    private Context context;

    private Form form;

    private static final int ADDED_PAGES = 3;

    private static final int PAGES_TO_REDUCE = 2;

    public static final String QUESTION = "question";

    public static final String LAST_PAGE = "lastPage";

    private static final int DECREMENTER = 1;

    private Session session;

    private Map<Integer, Fragment> mappedFragments;

    private Mentorship mentorship;

    public SwipeAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
        this.form = new Form();
        this.mappedFragments = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();

        bundle.putSerializable("session", session);
        bundle.putSerializable("form", form);

        switch (position) {
            case 0:
                return new FormsFragment();
            case 1:
                return new TutoredFragment();
            case 2:
                HealthFacilityFragment fragment = new HealthFacilityFragment();
                fragment.setArguments(bundle);
                return fragment;
        }


        if (position == 3 && FormType.MENTORING_CUSTOM.equals(form.getFormType())) {
            bundle.putSerializable("iterationType", mentorship.getIterationType());

            IterationTypeFragment iterationTypeFragment = new IterationTypeFragment();
            iterationTypeFragment.setArguments(bundle);

            return iterationTypeFragment;
        }

        Fragment mappedFragment = this.getFragment(position - DECREMENTER);

        bundle.putString("target", getTarget());
        bundle.putSerializable("session", session);
        bundle.putInt("iterationTypeImg", getIterationType());

        if (mappedFragment != null) {

            if (mappedFragment instanceof ConfirmationFragment) {
                mappedFragment.setArguments(bundle);
                return mappedFragment;
            }

            if (mappedFragment instanceof SaveFragment) {
                bundle.putSerializable("category", QuestionCategory.FEEDBACK_QUESTIONS);
                mappedFragment.setArguments(bundle);
                return mappedFragment;
            }
        }

        Fragment fragment = new QuestionFragment();

        bundle.putSerializable("category", form.getQuestionCategoryByPosition(position - ADDED_PAGES));

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return form.size() + ADDED_PAGES + mappedFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getResources().getString(R.string.form);
            case 1:
                return context.getResources().getString(R.string.tutored);
            case 2:
                return this.context.getResources().getString(R.string.health_facility);
        }

        if (position == 3 && FormType.MENTORING_CUSTOM.equals(form.getFormType())) {
            return context.getResources().getString(R.string.iteration_type);
        }

        Fragment mappedFragment = this.getFragment(position - DECREMENTER);

        if (mappedFragment != null) {

            if (mappedFragment instanceof ConfirmationFragment) {
                return context.getResources().getString(R.string.confirmation);
            }

            if (mappedFragment instanceof SaveFragment) {
                return context.getResources().getString(R.string.submission);
            }
        }

        return form.getQuestionCategoryByPosition(position - PAGES_TO_REDUCE - DECREMENTER);
    }

    public void setForm(Form form) {
        this.form = form;
        this.form.getQuestionCategories();
        notifyDataSetChanged();
    }

    public void addFragment(int key, Fragment fragment) {
        this.mappedFragments.put(key, fragment);
        notifyDataSetChanged();
    }

    public Fragment getFragment(int key) {
        return this.mappedFragments.get(key);
    }

    public int getQuestionsSize() {
        return form.size() + ADDED_PAGES;
    }


    public void setSession(Session session) {
        this.session = session;
    }

    private String getTarget() {

        if (IterationType.FILE.equals(mentorship.getIterationType())) {
            return (session.performedByIterationType(IterationType.FILE) + DECREMENTER) + "/" + form.getTargetFile();
        }

        return (session.performedByIterationType(IterationType.PATIENT) + DECREMENTER) + "/" + form.getTargetPatient();
    }

    public int getIterationType() {

        if (IterationType.PATIENT.equals(mentorship.getIterationType())) {
            return R.mipmap.ic_patient;
        }

        return R.mipmap.ic_file;
    }

    public void setMentorship(Mentorship mentorship) {
        this.mentorship = mentorship;
    }
}

