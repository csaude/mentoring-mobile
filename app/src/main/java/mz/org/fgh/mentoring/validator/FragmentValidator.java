package mz.org.fgh.mentoring.validator;

import android.support.v4.view.ViewPager;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;

/**
 * Created by Stélio Moiane on 8/4/17.
 */
public interface FragmentValidator {

    void validate(ViewPager viewPager, int position);
}
