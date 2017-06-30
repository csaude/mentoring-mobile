package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Stélio Moiane on 6/19/17.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(getResourceId(), container, false);
        ButterKnife.bind(this, view);

        this.onCreateView();

        return view;
    }

    public abstract int getResourceId();

    public abstract void onCreateView();
}
