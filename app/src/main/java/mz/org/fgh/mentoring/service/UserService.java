package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.infra.UserContext;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Stélio Moiane on 5/22/17.
 */
public interface UserService {

    @POST("users")
    Call<UserContext> login(@Body final UserContext userContext);
}
