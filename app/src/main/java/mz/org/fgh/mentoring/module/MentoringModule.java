package mz.org.fgh.mentoring.module;

import android.content.Context;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dagger.Module;
import dagger.Provides;
import mz.org.fgh.mentoring.config.dao.*;
import mz.org.fgh.mentoring.process.dao.*;
import mz.org.fgh.mentoring.service.*;
import mz.org.fgh.mentoring.util.CustomJacksonDateDeserializer;
import mz.org.fgh.mentoring.util.ServerConfig;
import mz.org.fgh.mentoring.validator.TextViewValidator;
import okhttp3.OkHttpClient;
import org.greenrobot.eventbus.EventBus;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Stélio Moiane on 6/28/17.
 */

@Module
public class MentoringModule {

    private Context context;

    private final ObjectMapper mapper;

    private final OkHttpClient okHttpClient;

    public MentoringModule(Context context) {
        this.context = context;

        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new SimpleModule()
                .addDeserializer(Date.class, new CustomJacksonDateDeserializer()));
        this.mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    public LoadMetadataService provideLoadMetadataService(LoadMetadataServiceImpl loadMetadataService) {
        return loadMetadataService;
    }

    @Provides
    @Named("mentoring")
    public Retrofit provideMentoringRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerConfig.MENTORING.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        return retrofit;
    }

    @Provides
    @Named("account")
    public Retrofit provideAccontRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerConfig.ACCOUNT_MANAGER.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

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
    public MentorshipDAO provideMentorshipDAO() {
        return new MentorshipDAOImpl(context);
    }

    @Provides
    public IndicatorDAO provideIndicatorDAO() {
        return new IndictorDAOImpl(context);
    }

    @Provides
    public AnswerDAO provideAnswerDAO() {
        return new AnswerDAOImpl(context);
    }

    @Provides
    public SessionDAO provideSessionDAO() {
        return new SessionDAOImpl(context);
    }

    @Provides
    public SettingDAO provideSettingDAO() {
        return new SettingDAOImpl(context);
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
    public IndicatorService provideIndicatorService(IndicatorServiceImpl indicatorServiceImpl) {
        return indicatorServiceImpl;
    }

    @Provides
    public SessionService provideSessionService(SessionServiceImpl sessionService) {
        return sessionService;
    }

    @Provides
    public MentorshipService provideMentorshipService(MentorshipServiceImpl mentorshipService) {
        return mentorshipService;
    }

    @Provides
    @Named("sessions")
    public SyncService provideSyncService(MentorshipSyncServiceImpl syncService) {
        return syncService;
    }

    @Provides
    @Singleton
    public EventBus provideEventBus() {
        return EventBus.builder().build();
    }

    @Provides
    public TextViewValidator provideTextViewValidator() {
        return new TextViewValidator(context);
    }

    @Provides
    public CabinetDAO proviceCabinetDAO() {
        return new CabinetDAOImpl(context);
    }

    @Provides
    public CabinetService provideCabinetService(CabinetServiceImpl cabinetService) {
        return cabinetService;
    }

    @Provides
    public FormTargetDAO provideFormTargetDAO() {
        return new FormTargetDAOImpl(context);
    }

    @Provides
    public FormTargetService proviceFormTargetService(FormTargetServiceImpl formTargetService) {
        return formTargetService;
    }

    @Provides
    public TutoredSyncService provideTutoredSyncService(TutoredSyncServiceImpl tutoredSyncService) {
        return tutoredSyncService;
    }

    @Provides
    public SettingSyncService provideSettingSyncService(SettingSyncServiceImpl settingSyncService) {
        return settingSyncService;
    }
}
