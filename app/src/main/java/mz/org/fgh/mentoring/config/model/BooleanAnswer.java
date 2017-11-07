/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.config.model;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class BooleanAnswer extends Answer {

    private Boolean booleanValue;

    @Override
    public void setValue(final String value) {
        this.booleanValue = Boolean.valueOf(value);
    }

    @Override
    public String getValue() {
        return String.valueOf(booleanValue);
    }
}
