package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.widget.Toast;

import mz.org.fgh.mentoring.activities.ConfigurationActivity;
import mz.org.fgh.mentoring.config.dao.DistrictDAO;
import mz.org.fgh.mentoring.config.dao.DistrictDAOImpl;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAO;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAOImpl;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 11/12/16.
 */
public class HealthFacilitySyncServiceImpl implements SyncService {

    private ConfigurationActivity activity;

    @Override
    public void execute() {

        MentoringApplication application = (MentoringApplication) activity.getApplication();
        Retrofit retrofit = application.getRetrofit();
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);
        Call<GenericWrapper> call = syncDataService.healthFacilities();
        final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A receber dados....", true, true);

        call.enqueue(new Callback<GenericWrapper>() {

                         @Override
                         public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {

                             GenericWrapper wrapper = response.body();
                             DistrictDAO districtDAO = new DistrictDAOImpl(activity);
                             HealthFacilityDAO healthFacilityDAO = new HealthFacilityDAOImpl(activity);

                             for (HealthFacility healthFacility : wrapper.getHealthFacilities()) {

                                 if (!districtDAO.exist(healthFacility.getDistrict().getDistrict())) {
                                     districtDAO.create(healthFacility.getDistrict());
                                 }

                                 if (!healthFacilityDAO.exist(healthFacility.getHealthFacility())) {
                                     healthFacilityDAO.create(healthFacility);
                                 }
                             }

                             Toast.makeText(activity, healthFacilityDAO.findAll().size() + " Unidades Sanitárias foram sincronizadas com sucesso!", Toast.LENGTH_SHORT).show();

                             districtDAO.close();
                             healthFacilityDAO.close();

                             dialog.dismiss();
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
