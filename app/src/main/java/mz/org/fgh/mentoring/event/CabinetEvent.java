package mz.org.fgh.mentoring.event;

import mz.org.fgh.mentoring.config.model.Cabinet;

/**
 * Created by steliomo on 4/12/18.
 */

public class CabinetEvent {

    private Cabinet cabinet;

    public CabinetEvent(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }
}
