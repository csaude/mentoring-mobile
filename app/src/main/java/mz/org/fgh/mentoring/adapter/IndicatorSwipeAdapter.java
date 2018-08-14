package mz.org.fgh.mentoring.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.fragment.FormsFragment;
import mz.org.fgh.mentoring.fragment.HealthFacilityFragment;
import mz.org.fgh.mentoring.fragment.IndicatorHealthFacilityFragment;
import mz.org.fgh.mentoring.fragment.TutoredFragment;

/**
 * Created by Stélio Moiane on 11/14/16.
 */
public class IndicatorSwipeAdapter extends FragmentStatePagerAdapter {

    private List<Question> questions = new ArrayList<>();
    private int position;

    private static final int ADDED_PAGES = 2;
    private static final int PAGES_TO_REDUCE = 1;

    public static final String QUESTION = "question";
    public static final String LAST_PAGE = "lastPage";

    public IndicatorSwipeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        this.position = position;

        switch (position) {
            case 0:
                return new FormsFragment();
            case 1:
                return new IndicatorHealthFacilityFragment();
        }

        Question question = questions.get(position - ADDED_PAGES);
        Fragment fragment = question.getQuestionType().getFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION, questions.get(position - ADDED_PAGES));
        bundle.putBoolean(LAST_PAGE, isLastPage());

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return questions.size() + ADDED_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String step = "";

        switch (position) {
            case 0:
                step = "Formulários";
                break;

            case 1:
                step = "Unidade Sanitária";
                break;

            default:
                step = "Questão -> " + (position - PAGES_TO_REDUCE);
        }

        return step;
    }

    public boolean isLastPage() {
        return (++position) == questions.size() + ADDED_PAGES;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
    }
}
