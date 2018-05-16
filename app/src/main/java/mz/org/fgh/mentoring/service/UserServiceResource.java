package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.infra.UserContext;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Stélio Moiane on 5/22/17.
 */
public interface UserServiceResource {

    @POST("users")
    Call<UserContext> login(@Body final UserContext userContext);

    @PUT("users/update")
    Call<UserContext> changePassword(@Body final UserContext userContext);

    @PUT("tutors/reset-password")
    Call<Tutor> resetPassword(@Body final UserContext userContext);
}
