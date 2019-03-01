package mz.org.fgh.mentoring.infra;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;
import mz.org.fgh.mentoring.component.DaggerMentoringComponent;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.module.MentoringModule;
import mz.org.fgh.mentoring.util.ServerConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public class MentoringApplication extends Application {

    private Auth auth;
    private SharedPreferences sharedPreferences;
    private Retrofit mentoringRetrofit;
    private Retrofit accountRetrofit;
    private ObjectMapper mapper;
    private MentoringComponent mentoringComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        sharedPreferences = getSharedPreferences(MentoringApplication.class.getName(), Context.MODE_PRIVATE);

        mapper = new ObjectMapper();

        auth = new Auth(this, getUserContext(UserContext.USER_CONTEXT));

        mentoringComponent = DaggerMentoringComponent.builder().mentoringModule(new MentoringModule(this)).build();

        // Try if the token exists.
        String jwtToken = getSharedPreferences().getString(UserContext.JWT_TOKEN_NAME, null);
        setupAccountRetrofit(jwtToken);
        setupMentoringRetrofit(jwtToken);
    }

    public void setupMentoringRetrofit(final String jwtToken) {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(ServerConfig.MENTORING.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create(mapper));

        if(jwtToken != null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request modified = original.newBuilder()
                            .addHeader("Authorization", "Bearer " + jwtToken)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(modified);
                }
            });

            OkHttpClient client = clientBuilder.build();
            retrofitBuilder.client(client);
        }

        mentoringRetrofit = retrofitBuilder.build();
    }

    public void setupMentoringRetrofit() {
        setupMentoringRetrofit(null);
    }

    public void setupAccountRetrofit(final String jwtToken) {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(ServerConfig.ACCOUNT_MANAGER.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create(mapper));

        if(jwtToken != null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request modified = original.newBuilder()
                            .addHeader("Authorization", "Bearer " + jwtToken)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(modified);
                }
            });

            OkHttpClient client = clientBuilder.build();
            retrofitBuilder.client(client);
        }

        accountRetrofit = retrofitBuilder.build();
    }

    public void setupAccountRetrofit() {
        setupAccountRetrofit(null);
    }
    public Auth getAuth() {
        return this.auth;
    }

    public SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    public Retrofit getMentoringRetrofit() {
        return mentoringRetrofit;
    }

    public Retrofit getAccountRetrofit() {
        return accountRetrofit;
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
