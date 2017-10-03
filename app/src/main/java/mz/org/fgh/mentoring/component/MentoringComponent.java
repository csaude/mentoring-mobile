package mz.org.fgh.mentoring.component;

import javax.inject.Singleton;

import dagger.Component;
import mz.org.fgh.mentoring.activities.ChangePasswordActivity;
import mz.org.fgh.mentoring.activities.LoginActivity;
import mz.org.fgh.mentoring.activities.MainActivity;
import mz.org.fgh.mentoring.activities.TutoredActivity;
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
}
