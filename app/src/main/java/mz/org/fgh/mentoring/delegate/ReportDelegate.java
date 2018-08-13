package mz.org.fgh.mentoring.delegate;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.PerformedSession;
import mz.org.fgh.mentoring.infra.UserContext;

public interface ReportDelegate {

    Form getForm();

    UserContext getUser();

    void showResultFragment(List<PerformedSession> performedSessions);

    void showCurrentStageReport(List<PerformedSession> performedSessions);

    void showPeriodFragment();

    List<PerformedSession> getPerformedSessions();

    void setStartDate(String startDate);

    void setEndDate(String endDate);

    String getStartDate();

    String getEndDate();

    int getTarget();
}
