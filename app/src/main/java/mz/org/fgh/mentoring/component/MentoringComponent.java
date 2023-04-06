package mz.org.fgh.mentoring.component;

import dagger.Component;
import mz.org.fgh.mentoring.activities.*;
import mz.org.fgh.mentoring.adapter.MentorshipAdapter;
import mz.org.fgh.mentoring.adapter.QuestionAdapter;
import mz.org.fgh.mentoring.fragment.*;
import mz.org.fgh.mentoring.module.MentoringModule;

import javax.inject.Singleton;

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

    void inject(ListMentorshipActivity activity);

    void inject(QuestionAdapter adapter);

    void inject(ConfirmationFragment fragment);

    void inject(SaveFragment fragment);

    void inject(ResetPasswordActivity activity);

    void inject(ReportActivity activity);

    void inject(PeriodFragment fragment);

    void inject(ReportTypeFragment fragment);

    void inject(IterationTypeFragment fragment);

    void inject(QuestionFragment fragment);

    void inject(TextFragment fragment);

    void inject(ListTutoredActivity activity);

    void inject(SessionsReportActivity activity);

    void inject(SessionReportFragment fragment);

    void inject(MentorshipAdapter adapter);
}
