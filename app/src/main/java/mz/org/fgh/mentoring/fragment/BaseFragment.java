package mz.org.fgh.mentoring.fragment;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.infra.MentoringApplication;

/**
 * Created by Stélio Moiane on 6/19/17.
 */

public abstract class BaseFragment extends Fragment {

    protected MentoringApplication application;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getResourceId(), container, false);
        ButterKnife.bind(this, view);

        BaseActivity activity = (BaseActivity) getActivity();
        application = (MentoringApplication) activity.getApplication();

        this.onCreateView();

        return view;
    }

    public abstract int getResourceId();

    public abstract void onCreateView();
}
