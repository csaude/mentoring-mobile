package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Stélio Moiane on 10/25/16.
 */
public interface SyncDataService {

    @GET("healthfacilities/{districtId}")
    Call<GenericWrapper> healthFacilities(@Path("districtId") final Long districtId);
}
