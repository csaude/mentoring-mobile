package mz.org.fgh.mentoring.component;

import javax.inject.Singleton;

import dagger.Component;
import mz.org.fgh.mentoring.activities.ChangePasswordActivity;
import mz.org.fgh.mentoring.activities.IndicatorActivity;
import mz.org.fgh.mentoring.activities.ListIndicatorsActivity;
import mz.org.fgh.mentoring.activities.LoginActivity;
import mz.org.fgh.mentoring.activities.MainActivity;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.activities.TutoredActivity;
import mz.org.fgh.mentoring.fragment.BaseFragment;
import mz.org.fgh.mentoring.fragment.BooleanFragment;
import mz.org.fgh.mentoring.fragment.FormsFragment;
import mz.org.fgh.mentoring.fragment.HealthFacilityFragment;
import mz.org.fgh.mentoring.fragment.IndicatorHealthFacilityFragment;
import mz.org.fgh.mentoring.fragment.NumericFragment;
import mz.org.fgh.mentoring.fragment.PageFragment;
import mz.org.fgh.mentoring.fragment.TutoredFragment;
import mz.org.fgh.mentoring.module.MentoringModule;

/**
 * Created by Stélio Moiane on 6/28/17.
 */
@Singleton
@Component(modules = MentoringModule.class)
public interface MentoringComponent {

    void inject(MainActivity activity);

    void inject(ChangePasswordActivity activity);

    void inject(TutoredActivity activity);

    void inject(LoginActivity activity);

    void inject(MentoringActivity activity);

    void inject(ListIndicatorsActivity activity);

    void inject(IndicatorActivity activity);

    void inject(FormsFragment fragment);

    void inject(HealthFacilityFragment fragment);

    void inject(PageFragment fragment);

    void inject(BooleanFragment fragment);

    void inject(NumericFragment fragment);

    void inject(TutoredFragment fragment);

    void inject(IndicatorHealthFacilityFragment fragment);
}
