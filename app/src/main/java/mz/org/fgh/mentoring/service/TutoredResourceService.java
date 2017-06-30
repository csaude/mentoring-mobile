package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Stélio Moiane on 5/31/17.
 */
public interface TutoredResourceService {

    @GET("tutoreds/{userUuid}")
    Call<GenericWrapper> findTutoredsByUser(@Path("userUuid") final String userUuid);
}
