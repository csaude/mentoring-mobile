package mz.org.fgh.mentoring.event;

/**
 * Created by steliomo on 10/26/17.
 */

public class ProcessEvent {

    private EventType eventType;

    private String reason;

    public ProcessEvent(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
