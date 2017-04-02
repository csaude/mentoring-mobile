package mz.org.fgh.mentoring.infra;

import android.app.Application;
import android.content.SharedPreferences;

import mz.org.fgh.mentoring.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        retrofit = new Retrofit.Builder().baseUrl("http://" +
                sharedPreferences.getString(getResources().getString(R.string.serve_address), "localhost") +
                "/mentoring-integ/services/").addConverterFactory(GsonConverterFactory.create())
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
}
