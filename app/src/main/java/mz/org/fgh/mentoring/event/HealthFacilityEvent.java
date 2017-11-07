package mz.org.fgh.mentoring.event;

import mz.org.fgh.mentoring.config.model.HealthFacility;

/**
 * Created by steliomo on 10/26/17.
 */

public class HealthFacilityEvent {

    private HealthFacility healthFacility;

    public HealthFacilityEvent(HealthFacility healthFacility) {
        this.healthFacility = healthFacility;
    }

    public HealthFacility getHealthFacility() {
        return healthFacility;
    }
}
