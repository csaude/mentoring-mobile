package mz.org.fgh.mentoring.config.model;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public enum QuestionType {

    TEXT {
        @Override
        public Answer getAnswer() {
            return new TextAnswer();
        }
    },

    BOOLEAN {
        @Override
        public Answer getAnswer() {
            return new BooleanAnswer();
        }
    },

    NUMERIC {
        @Override
        public Answer getAnswer() {
            return null;
        }
    },

    DECIMAL {
        @Override
        public Answer getAnswer() {
            return null;
        }
    },

    CURRENCY {
        @Override
        public Answer getAnswer() {
            return null;
        }
    };

    public abstract Answer getAnswer();
}
