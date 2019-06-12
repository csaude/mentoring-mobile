package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.config.model.PerformedSession;
import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReportResource {

    @GET("mentorships/performed-sessions-by-tutor")
    Call<GenericWrapper> findPerformedSessionsByTutor(@Query("tutorUuid") final String tutorUuid, @Query("startDate") final String startDate, @Query("endDate") final String endDate);

}
