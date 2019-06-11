package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.model.Tutored;

public interface TutoredSyncService {
    void syncTutoreds(UserContext userContext, List<Tutored> tutoreds);

    void setActivity(BaseActivity activity);
}
