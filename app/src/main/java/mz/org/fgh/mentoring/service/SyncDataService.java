package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Stélio Moiane on 10/25/16.
 */
public interface SyncDataService {

    @GET("healthfacilities")
    Call<GenericWrapper> healthFacilities();

    @GET("careers")
    Call<GenericWrapper> careers();

    @GET("programmaticareas")
    Call<GenericWrapper> programmaticareas();


}
