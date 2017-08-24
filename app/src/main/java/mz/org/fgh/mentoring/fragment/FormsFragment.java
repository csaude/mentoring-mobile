package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.adapter.FormAdapter;
import mz.org.fgh.mentoring.config.dao.FormDAOImpl;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class FormsFragment extends BaseFragment implements AdapterView.OnItemClickListener, FragmentValidator {

    @BindView(R.id.fragment_forms)
    ListView formsListView;

    private MentoringActivity activity;

    private Bundle activityBundle;

    private Form form;

    @Override
    public void onCreateView() {
        activity = (MentoringActivity) getActivity();
        activityBundle = activity.getBundle();

        FormDAOImpl formDAO = new FormDAOImpl(activity);
        List<Form> forms = formDAO.findAll();
        formDAO.close();

        FormAdapter adapter = new FormAdapter(activity, forms);
        formsListView.setAdapter(adapter);

        formsListView.setOnItemClickListener(this);
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_forms;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        form = (Form) parent.getItemAtPosition(position);
        view.setSelected(true);

        activityBundle.putSerializable("form", form);
        activity.getAdapter().setQuestions(new QuestionDAOImpl(activity).findQuestionByForm(form.getUuid()));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (form != null) {
            return;
        }

        viewPager.setCurrentItem(position);
        Snackbar.make(getView(), getString(R.string.form_must_be_selected), Snackbar.LENGTH_SHORT).show();
    }
}
