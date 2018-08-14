package mz.org.fgh.mentoring.config.model;

/**
 * Created by steliomo on 10/10/17.
 */

public class NumericAnswer extends Answer {

    private Integer value;

    @Override
    public void setValue(String value) {
        this.value = Integer.valueOf(value);
    }

    @Override
    public String getValue() {
        return String.valueOf(this.value);
    }
}
