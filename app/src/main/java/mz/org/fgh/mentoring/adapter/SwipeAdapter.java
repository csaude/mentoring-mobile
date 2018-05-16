package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.QuestionCategory;
import mz.org.fgh.mentoring.config.model.QuestionType;
import mz.org.fgh.mentoring.fragment.ConfirmationFragment;
import mz.org.fgh.mentoring.fragment.FormsFragment;
import mz.org.fgh.mentoring.fragment.HealthFacilityFragment;
import mz.org.fgh.mentoring.fragment.QuestionFragment;
import mz.org.fgh.mentoring.fragment.SaveFragment;
import mz.org.fgh.mentoring.fragment.TutoredFragment;
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

    public SwipeAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
        this.form = new Form();
        this.mappedFragments = new HashMap<>();
        this.session = new Session();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new HealthFacilityFragment();
            case 1:
                return new TutoredFragment();
            case 2:
                return new FormsFragment();
        }

        Fragment mappedFragment = this.getFragment(position - DECREMENTER);

        Bundle bundle = new Bundle();
        bundle.putSerializable("form", form);
        bundle.putString("target", getTarget());
        bundle.putSerializable("session", session);

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
                return this.context.getResources().getString(R.string.health_facility);

            case 1:
                return context.getResources().getString(R.string.tutored);

            case 2:
                return context.getResources().getString(R.string.form);
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

        return form.getQuestionCategoryByPosition(position - PAGES_TO_REDUCE - DECREMENTER).getValue();
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
        return (session.getMentorships().size() + DECREMENTER) + "/" + form.getTarget();
    }
}
