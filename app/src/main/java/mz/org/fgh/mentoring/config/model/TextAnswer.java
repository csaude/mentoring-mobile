/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.config.model;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class TextAnswer extends Answer {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "TEXT";

    private String textValue;

    public String getTextValue() {
        return this.textValue;
    }

    @Override
    public void setValue(final String value) {
        this.textValue = value;
    }
}
