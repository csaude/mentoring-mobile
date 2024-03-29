package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.FormTarget;
import mz.org.fgh.mentoring.config.model.PerformedSession;
import mz.org.fgh.mentoring.delegate.ReportDelegate;
import mz.org.fgh.mentoring.fragment.PeriodFragment;
import mz.org.fgh.mentoring.fragment.ReportCurrentStageFragment;
import mz.org.fgh.mentoring.fragment.ReportResultFragment;
import mz.org.fgh.mentoring.fragment.ReportTypeFragment;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.service.FormTargetService;

public class ReportActivity extends BaseAuthenticateActivity implements ReportDelegate, View.OnClickListener {

    @Inject
    FormTargetService formTargetService;

    private List<PerformedSession> performedSessions;
    private String startDate;
    private String endDate;
    private FragmentManager fragmentManager;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_report);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(this);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        showFragment(new ReportTypeFragment(), Boolean.FALSE);
    }

    private void showFragment(Fragment fragment, boolean onStack) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.report_frame_layout, fragment);

        if (onStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public UserContext getUser() {
        return application.getAuth().getUser();
    }


    @Override
    public List<PerformedSession> getPerformedSessions() {
        return this.performedSessions;
    }

    @Override
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getStartDate() {
        return this.startDate;
    }

    @Override
    public String getEndDate() {
        return this.endDate;
    }

    @Override
    public int getTarget() {
        FormTarget target = formTargetService.findFormTargetByCareerUuid(getUser().getTutor().getCareer().getUuid());

        if (target == null) {
            return 0;
        }

        return target.getTarget();
    }

    @Override
    public int getTotalPerformed() {

        int totalPerformed = 0;

        for (PerformedSession performedSession : performedSessions) {
            totalPerformed = totalPerformed + performedSession.getTotalPerformed();
        }

        return totalPerformed;
    }

    @Override
    public void showResultFragment(List<PerformedSession> performedSessions) {
        this.performedSessions = performedSessions;
        showFragment(new ReportResultFragment(), Boolean.TRUE);
    }

    @Override
    public void showCurrentStageReport(List<PerformedSession> performedSessions) {

        this.performedSessions = performedSessions;
        showFragment(new ReportCurrentStageFragment(), Boolean.TRUE);

    }

    @Override
    public void showPeriodFragment() {
        showFragment(new PeriodFragment(), Boolean.TRUE);
    }

    @Override
    public void onClick(View view) {

        if (fragmentManager.getBackStackEntryCount() == 0) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        fragmentManager.popBackStack();
    }
}