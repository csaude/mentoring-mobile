package mz.org.fgh.mentoring.config.model;

import android.support.v4.app.Fragment;

import mz.org.fgh.mentoring.fragment.BooleanFragment;
import mz.org.fgh.mentoring.fragment.NumericFragment;
import mz.org.fgh.mentoring.fragment.PageFragment;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public enum QuestionType {

    TEXT {
        @Override
        public Answer getAnswer() {
            return new TextAnswer();
        }

        @Override
        public Fragment getFragment() {
            return new PageFragment();
        }
    },

    BOOLEAN {
        @Override
        public Answer getAnswer() {
            return new BooleanAnswer();
        }

        @Override
        public Fragment getFragment() {
            return new BooleanFragment();
        }
    },

    NUMERIC {
        @Override
        public Answer getAnswer() {
            return new NumericAnswer();
        }

        @Override
        public Fragment getFragment() {
            return new NumericFragment();
        }
    },

    DECIMAL {
        @Override
        public Answer getAnswer() {
            return null;
        }

        @Override
        public Fragment getFragment() {
            return null;
        }
    },

    CURRENCY {
        @Override
        public Answer getAnswer() {
            return null;
        }

        @Override
        public Fragment getFragment() {
            return null;
        }
    };

    public abstract Answer getAnswer();

    public abstract Fragment getFragment();
}
