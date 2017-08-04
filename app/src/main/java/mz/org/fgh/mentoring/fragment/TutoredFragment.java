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
import mz.org.fgh.mentoring.adapter.TutoredItemAdapter;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.config.dao.TutoredDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.validator.FragmentValidator;


public class TutoredFragment extends BaseFragment implements AdapterView.OnItemClickListener, FragmentValidator {

    @BindView(R.id.fragment_tutoreds)
    ListView tutoredsList;

    private Bundle activityBundle;

    private MentoringActivity activity;
    private Tutored selectedTutored;

    @Override
    public void onCreateView() {

        activity = (MentoringActivity) getActivity();
        activityBundle = activity.getBundle();

        TutoredDAO tutoredDAO = new TutoredDAOImpl(activity);
        List<Tutored> tutoreds = tutoredDAO.findAll();

        TutoredItemAdapter adapter = new TutoredItemAdapter(activity, tutoreds);
        tutoredsList.setAdapter(adapter);

        tutoredsList.setOnItemClickListener(this);

        tutoredDAO.close();
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_tutoreds;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        selectedTutored = (Tutored) parent.getItemAtPosition(position);
        activityBundle.putSerializable("tutored", selectedTutored);
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (selectedTutored != null) {
            return;
        }

        viewPager.setCurrentItem(position);
        Snackbar.make(getView(), getString(R.string.tutored_must_be_selected), Snackbar.LENGTH_SHORT).show();
    }
}
