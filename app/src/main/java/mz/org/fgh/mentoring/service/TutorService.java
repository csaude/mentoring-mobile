package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.model.TutorBeanResource;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Stélio Moiane on 5/24/17.
 */
public interface TutorService {

    @GET("tutors/{uuid}")
    Call<Tutor> findTutorByUuid(@Path("uuid") final String uuid);

    @POST("tutors")
    Call<Void> createTutor(@Body TutorBeanResource tutorBeanResource);
}
