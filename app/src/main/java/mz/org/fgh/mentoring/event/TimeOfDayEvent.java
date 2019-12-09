package mz.org.fgh.mentoring.event;

/**
 * Created by Damaceno Lopes on 6/12/19.
 */
public class TimeOfDayEvent<T extends Object> {

    private T t;

    private String error;

    public TimeOfDayEvent(T t) {
        this.t = t;
    }

    public TimeOfDayEvent() {
    }

    public T getMessage() {
        return this.t;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setMessage(T t) {
        this.t = t;
    }
}
