package mz.org.fgh.mentoring.service;

import android.app.Activity;

import mz.org.fgh.mentoring.infra.UserContext;

/**
 * Created by steliomo on 11/3/17.
 */

public interface IndicatorService {

    void syncIndicators(final UserContext userContext);

    void setActivity(final Activity activity);

}
