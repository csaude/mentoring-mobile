package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.process.model.IndicatorBeanResource;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by steliomo on 11/2/17.
 */

public interface IndicatorServiceResource {

    @POST("indicators/sync")
    Call<IndicatorBeanResource> syncIndicators(@Body IndicatorBeanResource indicatorBeanResource);

}
