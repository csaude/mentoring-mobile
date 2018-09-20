package mz.org.fgh.mentoring.event;

public class ErrorEvent {

    private String message;

    public ErrorEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
