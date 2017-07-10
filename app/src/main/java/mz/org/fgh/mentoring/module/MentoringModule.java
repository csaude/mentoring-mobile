package mz.org.fgh.mentoring.module;

import android.content.Context;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.CareerDAOImpl;
import mz.org.fgh.mentoring.config.dao.DistrictDAO;
import mz.org.fgh.mentoring.config.dao.DistrictDAOImpl;
import mz.org.fgh.mentoring.config.dao.FormDAO;
import mz.org.fgh.mentoring.config.dao.FormDAOImpl;
import mz.org.fgh.mentoring.config.dao.FormQuestionDAO;
import mz.org.fgh.mentoring.config.dao.FormQuestionDAOImpl;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAO;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAOImpl;
import mz.org.fgh.mentoring.config.dao.QuestionDAO;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.config.dao.TutoredDAOImpl;
import mz.org.fgh.mentoring.service.CareerSyncService;
import mz.org.fgh.mentoring.service.CareerSyncServiceImpl;
import mz.org.fgh.mentoring.service.FormQuestionSyncService;
import mz.org.fgh.mentoring.service.FormQuestionSyncServiceImpl;
import mz.org.fgh.mentoring.service.HealthFacilitySyncService;
import mz.org.fgh.mentoring.service.HealthFacilitySyncServiceImpl;
import mz.org.fgh.mentoring.service.LoadMetadataService;
import mz.org.fgh.mentoring.service.LoadMetadataServiceImpl;
import mz.org.fgh.mentoring.service.TutoredService;
import mz.org.fgh.mentoring.service.TutoredServiceImpl;
import mz.org.fgh.mentoring.service.UserService;
import mz.org.fgh.mentoring.service.UserServiceImpl;
import mz.org.fgh.mentoring.util.ServerConfig;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Stélio Moiane on 6/28/17.
 */

@Module
public class MentoringModule {

    private Context context;

    private final ObjectMapper mapper;

    public MentoringModule(Context context) {
        this.context = context;

        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Provides
    public LoadMetadataService provideLoadMetadataService(LoadMetadataServiceImpl loadMetadataService) {
        return loadMetadataService;
    }

    @Provides
    @Named("mentoring")
    public Retrofit provideMentoringRetrofit() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://" + ServerConfig.MENTORING.getAddress() +
                ServerConfig.MENTORING.getService()).addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();

        return retrofit;
    }

    @Provides
    @Named("account")
    public Retrofit provideAccontRetrofit() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://" + ServerConfig.ACCOUNT_MANAGER.getAddress() +
                ServerConfig.ACCOUNT_MANAGER.getService()).addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();

        return retrofit;
    }

    @Provides
    public DistrictDAO provideDistrictDAO() {
        return new DistrictDAOImpl(context);
    }

    @Provides
    public HealthFacilityDAO provideHealthFacilityDAO() {
        return new HealthFacilityDAOImpl(context);
    }

    @Provides
    public CareerDAO provideCareerDAO() {
        return new CareerDAOImpl(context);
    }

    @Provides
    public FormDAO proviceFormDAO() {
        return new FormDAOImpl(context);
    }

    @Provides
    public QuestionDAO provideQuestionDAO() {
        return new QuestionDAOImpl(context);
    }

    @Provides
    public FormQuestionDAO provideFormQuestion() {
        return new FormQuestionDAOImpl(context);
    }

    @Provides
    public TutoredDAO provideTutoredDAO() {
        return new TutoredDAOImpl(context);
    }

    @Provides
    public HealthFacilitySyncService provideHealthFacilitySyncService(HealthFacilitySyncServiceImpl healthFacilitySyncService) {
        return healthFacilitySyncService;
    }

    @Provides
    public CareerSyncService provideCareerSyncService(CareerSyncServiceImpl careerSyncServiceImp) {
        return careerSyncServiceImp;
    }

    @Provides
    public FormQuestionSyncService provideFormQuestionSyncService(FormQuestionSyncServiceImpl formQuestionSyncServiceImpl) {
        return formQuestionSyncServiceImpl;
    }

    @Provides
    public TutoredService provideTutoredService(TutoredServiceImpl tutoredServiceImpl) {
        return tutoredServiceImpl;
    }

    @Provides
    public UserService provideUserService(UserServiceImpl userServiceImp) {
        return userServiceImp;
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.builder().build();
    }
}
