package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.activities.BaseActivity;
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
public class HealthFacilitySyncServiceImpl implements SyncService, HealthFacilitySyncService {

    private BaseActivity activity;

    @Inject
    DistrictDAO districtDAO;

    @Inject
    HealthFacilityDAO healthFacilityDAO;

    @Inject
    public HealthFacilitySyncServiceImpl() {
    }

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

                             GenericWrapper data = response.body();
                             if (data == null) {
                                 Toast.makeText(activity, "Problemas com a sincronização de dados. Verifique o endereço do servidor!", Toast.LENGTH_LONG).show();
                                 return;
                             }

                             processHealthFacilities(data.getHealthFacilities());

                             dialog.dismiss();
                         }

                         @Override
                         public void onFailure(Call<GenericWrapper> call, Throwable t) {
                             dialog.dismiss();
                             Log.e("Connection:--", t.getMessage());
                         }
                     }
        );

    }

    @Override
    public void processHealthFacilities(List<HealthFacility> healthFacilities) {

        //TODO: remove when implement Injection in all services
        if (activity != null) {
            districtDAO = new DistrictDAOImpl(activity);
            healthFacilityDAO = new HealthFacilityDAOImpl(activity);
        }

        for (HealthFacility healthFacility : healthFacilities) {

            if (!districtDAO.exist(healthFacility.getDistrict().getUuid())) {
                districtDAO.create(healthFacility.getDistrict());
            }

            if (!healthFacilityDAO.exist(healthFacility.getUuid())) {
                healthFacilityDAO.create(healthFacility);
            }
        }

        districtDAO.close();
        healthFacilityDAO.close();
    }

    @Override
    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void executeByUuids(String uuid) {

    }
}
