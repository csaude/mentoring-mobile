package mz.org.fgh.mentoring.event;

import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by steliomo on 10/26/17.
 */

public class TutoredEvent {

    private Tutored tutored;

    public TutoredEvent(final Tutored tutored) {
        this.tutored = tutored;
    }

    public Tutored getTutored() {
        return tutored;
    }
}
