package mz.org.fgh.mentoring.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.FormAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.delegate.FormDelegate;
import mz.org.fgh.mentoring.event.ErrorEvent;
import mz.org.fgh.mentoring.event.FormEvent;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class FormsFragment extends BaseFragment implements AdapterView.OnItemClickListener, FragmentValidator {

    @BindView(R.id.fragment_forms)
    ListView formsListView;

    @Inject
    EventBus eventBus;

    private Form form;

    private View oldView;

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        FormDelegate activity = (FormDelegate) getActivity();

        FormAdapter adapter = new FormAdapter(getActivity(), activity.getForms());
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

        this.toggleSelection(view);

        this.oldView = view;

        eventBus.post(new FormEvent(form));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (form != null) {
            return;
        }

        viewPager.setCurrentItem(position);
        eventBus.post(new ErrorEvent(getString(R.string.form_must_be_selected)));
    }


    public void toggleSelection(View view) {

        if (oldView != null) {
            oldView.findViewById(R.id.selected_row).setVisibility(View.GONE);
        }

        View selectedRow = view.findViewById(R.id.selected_row);
        selectedRow.setVisibility(View.VISIBLE);
        view.setSelected(true);
    }

    @Override
    public boolean isValid() {
        return form != null;
    }
}