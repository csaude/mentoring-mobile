package mz.org.fgh.mentoring.component;

import dagger.Component;
import mz.org.fgh.mentoring.activities.MainActivity;
import mz.org.fgh.mentoring.module.MentoringModule;

/**
 * Created by Stélio Moiane on 6/28/17.
 */

@Component(modules = MentoringModule.class)
public interface MentoringComponent {

    void inject(MainActivity activity);
}
