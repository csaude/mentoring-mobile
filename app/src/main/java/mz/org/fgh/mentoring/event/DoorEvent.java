package mz.org.fgh.mentoring.event;

/**
 * Created by Stélio Moiane on 7/10/17.
 */
public class DoorEvent<T extends Object> {

    private T t;

    private String error;

    public DoorEvent(T t) {
        this.t = t;
    }

    public DoorEvent() {
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
