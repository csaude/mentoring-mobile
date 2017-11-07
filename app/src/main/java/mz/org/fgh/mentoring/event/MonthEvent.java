package mz.org.fgh.mentoring.event;

import mz.org.fgh.mentoring.process.model.Month;

/**
 * Created by steliomo on 10/26/17.
 */

public class MonthEvent {

    private Month month;

    public MonthEvent(Month month) {
        this.month = month;
    }

    public Month getMonth() {
        return month;
    }
}
