package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.config.dao.FormDAOImpl;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Form;

public class FormsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private MentoringActivity activity;
    private Bundle activityBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forms, container, false);

        activity = (MentoringActivity) getActivity();
        activityBundle = activity.getBundle();


        FormDAOImpl formDAO = new FormDAOImpl(activity);
        List<Form> forms = formDAO.findAll();
        formDAO.close();

        ListView formsListView = (ListView) view.findViewById(R.id.fragment_forms);
        formsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<Form> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_single_choice, forms);
        formsListView.setAdapter(adapter);

        formsListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Form form = (Form) parent.getItemAtPosition(position);

        activityBundle.putSerializable("form", form);
        activity.getAdapter().setQuestions(new QuestionDAOImpl(activity).findQuestionByForm(form.getUuid()));
    }
}
