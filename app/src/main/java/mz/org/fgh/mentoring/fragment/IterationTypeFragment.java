package mz.org.fgh.mentoring.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.event.ErrorEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.process.model.IterationType;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.validator.FragmentValidator;


public class IterationTypeFragment extends BaseFragment implements FragmentValidator {

    @Inject
    EventBus eventBus;

    @BindView(R.id.fragment_iteration_type_patient)
    RadioButton patientIterationType;

    @BindView(R.id.fragment_iteration_type_file)
    RadioButton fileIterationType;

    @BindView(R.id.fragment_iteration_type_radio_group)
    RadioGroup iterationTypeGroup;

    private IterationType iterationType;

    private Session session;

    @Override
    public int getResourceId() {
        return R.layout.fragment_iteration_type;
    }

    @Override
    public void onCreateView() {
        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        Bundle arguments = getArguments();
        iterationType = (IterationType) arguments.get("iterationType");
        session = (Session) arguments.get("session");

        markIterationType(iterationType);
    }

    @OnClick(R.id.fragment_iteration_type_patient)
    public void onClickPatientIteration() {

        iterationType = IterationType.PATIENT;

        int performedPatients = session.performedByIterationType(iterationType);

        if (performedPatients >= session.getForm().getTargetPatient()) {
            iterationType = null;
            iterationTypeGroup.clearCheck();
            eventBus.post(new ErrorEvent(getString(R.string.patient_iterations_exceeded)));
            return;
        }

        eventBus.post(new MessageEvent<>(iterationType));
    }

    @OnClick(R.id.fragment_iteration_type_file)
    public void onClickFileIteration() {

        iterationType = IterationType.FILE;

        int performedFiles = session.performedByIterationType(iterationType);

        if (performedFiles >= session.getForm().getTargetFile()) {
            iterationType = null;
            iterationTypeGroup.clearCheck();
            eventBus.post(new ErrorEvent(getString(R.string.file_iterations_exceeded)));
            return;
        }

        eventBus.post(new MessageEvent<>(iterationType));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (iterationType != null) {
            return;
        }

        viewPager.setCurrentItem(position);
        eventBus.post(new ErrorEvent(getString(R.string.the_iteration_type_must_be_selected)));

    }

    @Override
    public boolean isValid() {
        return iterationType != null;
    }

    private void markIterationType(IterationType iterationType) {

        if (iterationType == null) {
            iterationTypeGroup.clearCheck();
            return;
        }

        switch (iterationType) {
            case PATIENT:
                patientIterationType.setChecked(Boolean.TRUE);
                break;

            case FILE:
                fileIterationType.setChecked(Boolean.TRUE);
                break;
        }
    }
}
