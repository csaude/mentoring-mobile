package mz.org.fgh.mentoring.infra;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.fabric.sdk.android.Fabric;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.Fabric;
import mz.org.fgh.mentoring.activities.BaseAuthenticateActivity;
import mz.org.fgh.mentoring.activities.ListMentorshipActivity;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.activities.SessionsReportActivity;
import mz.org.fgh.mentoring.component.DaggerMentoringComponent;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.module.MentoringModule;
import mz.org.fgh.mentoring.util.ServerConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public class MentoringApplication extends Application implements LifecycleObserver {

    private Auth auth;
    private SharedPreferences sharedPreferences;
    private Retrofit retrofit;
    private ObjectMapper mapper;
    private MentoringComponent mentoringComponent;

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onAppSentToBackground() {
        Log.d(getClass().getSimpleName(), "Application event ON_START");
        if(BaseAuthenticateActivity.instance != null &&
                !(BaseAuthenticateActivity.instance instanceof SessionsReportActivity) && !MentoringActivity.active && !ListMentorshipActivity.active) {
            Intent intent = new Intent(BaseAuthenticateActivity.instance, SessionsReportActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        sharedPreferences = getSharedPreferences(MentoringApplication.class.getName(), Context.MODE_PRIVATE);

        mapper = new ObjectMapper();

        auth = new Auth(this, getUserContext(UserContext.USER_CONTEXT));

        mentoringComponent = DaggerMentoringComponent.builder().mentoringModule(new MentoringModule(this)).build();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        setUpRetrofit(ServerConfig.MENTORING);
    }

    public void setUpRetrofit(final ServerConfig serverConfig) {

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(serverConfig.getProtocol() + "://" + serverConfig.getAddress() + serverConfig.getService())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .build();
    }

    public Auth getAuth() {
        return this.auth;
    }

    public SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    public void setUser(final UserContext user) {
        user.setLogged(true);
        auth.setUser(user);
        String userContext = "";

        try {
            userContext = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        sharedPreferences.edit().putString(UserContext.USER_CONTEXT, userContext).apply();
    }

    private UserContext getUserContext(String key) {
        String jsonUserContext = sharedPreferences.getString(key, "");

        if (jsonUserContext.isEmpty()) {
            return new UserContext();
        }

        UserContext userContext = null;

        try {
            userContext = mapper.readValue(jsonUserContext, UserContext.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userContext;
    }

    public void logout() {
        auth.setUser(new UserContext());
        sharedPreferences.edit().remove(UserContext.USER_CONTEXT).apply();
    }

    public MentoringComponent getMentoringComponent() {
        return mentoringComponent;
    }
}
