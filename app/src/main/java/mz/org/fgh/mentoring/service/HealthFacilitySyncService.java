package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.config.model.HealthFacility;

/**
 * Created by Stélio Moiane on 6/29/17.
 */
public interface HealthFacilitySyncService {

    public void processHealthFacilities(List<HealthFacility> healthFacilities);

}
