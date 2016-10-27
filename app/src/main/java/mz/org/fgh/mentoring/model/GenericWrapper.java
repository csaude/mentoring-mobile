package mz.org.fgh.mentoring.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Stélio Moiane on 10/25/16.
 */
public class GenericWrapper {

    @SerializedName("healthFacility")
    private List<HealthFacility> healthFacilities;

    public List<HealthFacility> getHealthFacilities() {
        return healthFacilities;
    }
}
