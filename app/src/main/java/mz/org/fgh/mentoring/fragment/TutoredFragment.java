package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.MentoringActivity;
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
        ArrayAdapter<Tutored> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, tutoreds);
        tutoredsList.setAdapter(adapter);

        tutoredsList.setOnItemClickListener(this);

        tutoredDAO.close();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tutored tutored = (Tutored) parent.getItemAtPosition(position);
        activityBundle.putSerializable("tutored", tutored);
        Toast.makeText(activity, "O tutorando " + tutored.getName() + " foi seleccionado", Toast.LENGTH_SHORT).show();
    }
}
