package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.IndicatorActivity;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.adapter.FormAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.FormDAO;
import mz.org.fgh.mentoring.config.dao.FormDAOImpl;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormType;
import mz.org.fgh.mentoring.event.FormEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class FormsFragment extends BaseFragment implements AdapterView.OnItemClickListener, FragmentValidator {

    @BindView(R.id.fragment_forms)
    ListView formsListView;

    @Inject
    FormDAO formDAO;

    @Inject
    EventBus eventBus;

    private Form form;

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        FragmentActivity activity = getActivity();

        List<Form> forms = new ArrayList<>();

        if (activity instanceof IndicatorActivity) {
            forms = formDAO.findByFormType(FormType.INDICATORS);
        } else if (activity instanceof MentoringActivity) {
            forms = formDAO.findByFormType(FormType.MENTORING);
        }

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

        eventBus.post(new FormEvent(form));
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
