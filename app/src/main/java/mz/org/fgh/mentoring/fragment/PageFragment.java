package mz.org.fgh.mentoring.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mz.org.fgh.mentoring.R;

public class PageFragment extends Fragment {

    private TextView textView;

    public PageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_frgament, container, false);
        textView = (TextView) view.findViewById(R.id.text_view);
        Bundle bundle = getArguments();
        textView.setText(" This is the page " + Integer.toString(bundle.getInt("count"))+"...." );

        return view;
    }


}
