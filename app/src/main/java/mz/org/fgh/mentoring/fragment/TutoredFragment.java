package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.adapter.TutoredItemAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.config.dao.TutoredDAOImpl;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.event.TutoredEvent;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.validator.FragmentValidator;


public class TutoredFragment extends BaseFragment implements AdapterView.OnItemClickListener, FragmentValidator {

    @BindView(R.id.fragment_tutoreds)
    ListView tutoredsList;

    @Inject
    TutoredDAO tutoredDAO;

    @Inject
    EventBus eventBus;

    private Tutored tutored;

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        List<Tutored> tutoreds = tutoredDAO.findAll();

        TutoredItemAdapter adapter = new TutoredItemAdapter(getActivity(), tutoreds);
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
        tutored = (Tutored) parent.getItemAtPosition(position);

        eventBus.post(new TutoredEvent(tutored));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (tutored != null) {
            return;
        }

        viewPager.setCurrentItem(position);
        Snackbar.make(getView(), getString(R.string.tutored_must_be_selected), Snackbar.LENGTH_SHORT).show();
    }
}
