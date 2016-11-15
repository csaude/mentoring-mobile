package mz.org.fgh.mentoring.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mz.org.fgh.mentoring.fragment.PageFragment;

/**
 * Created by Stélio Moiane on 11/14/16.
 */
public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("count", position + 1);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Question " + (position + 1);
    }
}
