/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.process.model;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class BooleanAnswer extends Answer {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "BOOLEAN";

    private Boolean booleanValue;

    public Boolean getBooleanValue() {
        return this.booleanValue;
    }

    @Override
    public void setValue(final String value) {
        this.booleanValue = Boolean.valueOf(value);
    }
}
