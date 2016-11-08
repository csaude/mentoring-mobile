package mz.org.fgh.mentoring.callback;

import android.app.ProgressDialog;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.ConfigurationActivity;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.model.GenericWrapper;
import mz.org.fgh.mentoring.service.HealthFacilityService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 10/24/16.
 */
public class ConfigCallback implements ActionMode.Callback {

    private ConfigurationActivity activity;

    public ConfigCallback(ConfigurationActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.config_menu_action_mode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.config_menu_sync:

                MentoringApplication application = (MentoringApplication) activity.getApplication();
                Retrofit retrofit = application.getRetrofit();
                HealthFacilityService healthFacilityService = retrofit.create(HealthFacilityService.class);
                Call<GenericWrapper> call = healthFacilityService.healthFacilities(1L);
                final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A receber dados....", true, true);

                call.enqueue(new Callback<GenericWrapper>() {

                                 @Override
                                 public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {
                                     dialog.dismiss();
                                     GenericWrapper wrapper = response.body();
                                     Toast.makeText(activity, wrapper.getHealthFacilities().get(5).getHealthFacility(), Toast.LENGTH_SHORT).show();

                                 }

                                 @Override
                                 public void onFailure(Call<GenericWrapper> call, Throwable t) {
                                     dialog.dismiss();
                                     Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                                 }
                             }
                );

                //Toast.makeText(activity, "good", Toast.LENGTH_SHORT).show();
                //activity.finish();
                return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        activity.clearSelection();
        activity.setActionMode(null);
    }
}
