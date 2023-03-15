package mz.org.fgh.mentoring.service;

import java.util.List;
import java.util.Map;

import mz.org.fgh.mentoring.model.GenericWrapper;
import mz.org.fgh.mentoring.model.TutoredBeanResource;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.MentorshipBeanResource;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Stélio Moiane on 10/25/16.
 */
public interface SyncDataService {

    @GET("healthfacilities/tutor/{uuid}")
    Call<GenericWrapper> healthFacilities(@Path("uuid") final String uuid);

    @GET("careers")
    Call<GenericWrapper> careers();

    @GET("formquestions")
    Call<GenericWrapper> formQuestions();

    @POST("tutoreds/sync")
    Call<TutoredBeanResource> syncTutoreds(@Body final TutoredBeanResource tutoredBeanResource);

    @POST("mentorships/sync")
    Call<MentorshipBeanResource> syncMentorships(@Body final MentorshipBeanResource mentorshipBeanResource);

    @GET("metadata/{uuid}")
    Call<GenericWrapper> loadMetadata(@Path("uuid") final String uuid);

    @GET("mentorships")
    Call<GenericWrapper> fetchMentorships(@QueryMap(encoded = true) Map<String, String> options);
}
