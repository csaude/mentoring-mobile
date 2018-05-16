package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by steliomo on 2/7/18.
 */

public abstract class BaseAbstractAdapter extends BaseAdapter {

    protected View view;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        this.view = convertView;

        if (this.view == null) {
            this.view = inflater.inflate(getResourceId(position), parent, false);
        }

        ButterKnife.bind(this, this.view);

        onCreateView(position);

        return this.view;
    }

    public abstract Context getContext();

    public abstract int getResourceId(int position);

    public abstract void onCreateView(int position);

    public View getView() {
        return view;
    }
}
