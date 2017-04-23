package mz.org.fgh.mentoring.infra;

import android.app.Application;
import android.content.SharedPreferences;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import mz.org.fgh.mentoring.R;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Stélio Moiane on 10/18/16.
 */
public class MentoringApplication extends Application {

    private Auth auth;
    private SharedPreferences sharedPreferences;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        auth = new Auth(this);
        sharedPreferences = getSharedPreferences(MentoringApplication.class.getName(), 0);

        setUpRetrofit();
    }

    private void setUpRetrofit() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        retrofit = new Retrofit.Builder().baseUrl("http://" +
                sharedPreferences.getString(getResources().getString(R.string.serve_address), "localhost") +
                "/mentoring-integ/services/").addConverterFactory(JacksonConverterFactory.create(mapper))
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

    public void updateRetrofit() {
        setUpRetrofit();
    }
}
