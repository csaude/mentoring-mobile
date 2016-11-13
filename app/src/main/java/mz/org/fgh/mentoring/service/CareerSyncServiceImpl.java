package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.widget.Toast;

import mz.org.fgh.mentoring.activities.ConfigurationActivity;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 11/12/16.
 */
public class CareerSyncServiceImpl implements SyncService {
    private ConfigurationActivity activity;

    @Override
    public void execute() {

        MentoringApplication application = (MentoringApplication) activity.getApplication();
        Retrofit retrofit = application.getRetrofit();
        SyncDataService healthFacilityService = retrofit.create(SyncDataService.class);
        Call<GenericWrapper> call = healthFacilityService.healthFacilities(1L);
        final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A receber  de carreiras...", true, true);

        call.enqueue(new Callback<GenericWrapper>() {

                         @Override
                         public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {
                             dialog.dismiss();
                             GenericWrapper wrapper = response.body();
                             Toast.makeText(activity, wrapper.getHealthFacilities() + "", Toast.LENGTH_SHORT).show();
                         }

                         @Override
                         public void onFailure(Call<GenericWrapper> call, Throwable t) {
                             dialog.dismiss();
                             Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
        );

    }

    @Override
    public void setActivity(ConfigurationActivity activity) {
        this.activity = activity;
    }
}
