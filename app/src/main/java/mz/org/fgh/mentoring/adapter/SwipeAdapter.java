package mz.org.fgh.mentoring.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import mz.org.fgh.mentoring.fragment.PageFragment;
import mz.org.fgh.mentoring.process.model.Question;

/**
 * Created by Stélio Moiane on 11/14/16.
 */
public class SwipeAdapter extends FragmentStatePagerAdapter {

    private List<Question> questions;
    private int position;

    public static final String QUESTION = "question";
    public static final String LAST_PAGE = "lastPage";

    public SwipeAdapter(FragmentManager fragmentManager, List<Question> questions) {
        super(fragmentManager);
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        this.position = position;

        PageFragment fragment = new PageFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION, questions.get(position));
        bundle.putBoolean(LAST_PAGE, isLastPage());

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Etapa -> " + (position + 1);
    }

    public boolean isLastPage() {
        return (position + 1) == questions.size();
    }
}
