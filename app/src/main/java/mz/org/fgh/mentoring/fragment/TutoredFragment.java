package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.adapter.TutoredItemAdapter;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.config.dao.TutoredDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;


public class TutoredFragment extends Fragment implements AdapterView.OnItemClickListener {

    private Bundle activityBundle;
    private MentoringActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tutoreds, container, false);

        activity = (MentoringActivity) getActivity();
        activityBundle = activity.getBundle();

        TutoredDAO tutoredDAO = new TutoredDAOImpl(activity);
        List<Tutored> tutoreds = tutoredDAO.findAll();

        ListView tutoredsList = (ListView) view.findViewById(R.id.fragment_tutoreds);
        TutoredItemAdapter adapter = new TutoredItemAdapter(activity, tutoreds);
        tutoredsList.setAdapter(adapter);

        tutoredsList.setOnItemClickListener(this);

        tutoredDAO.close();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        Tutored tutored = (Tutored) parent.getItemAtPosition(position);
        activityBundle.putSerializable("tutored", tutored);
    }
}
