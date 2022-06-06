package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.CareerDAOImpl;
import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 11/12/16.
 */
public class CareerSyncServiceImpl implements SyncService, CareerSyncService {

    @Inject
    CareerDAO careerDAO;

    private BaseActivity activity;

    @Inject
    public CareerSyncServiceImpl() {
    }

    @Override
    public void execute() {

        MentoringApplication application = (MentoringApplication) activity.getApplication();
        Retrofit retrofit = application.getRetrofit();
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);
        Call<GenericWrapper> call = syncDataService.careers();

        final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A receber dados....", true, true);

        call.enqueue(new Callback<GenericWrapper>() {

                         @Override
                         public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {

                             GenericWrapper data = response.body();

                             if (data == null) {
                                 Toast.makeText(activity, "Problemas com a sincronização de dados. Verifique o endereço do servidor!", Toast.LENGTH_LONG).show();
                                 return;
                             }

                             processCarres(data.getCareers());
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
    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void executeByUuids(String uuid) {

    }

    @Override
    public void processCarres(List<Career> careers) {

        //TODO: remove when complete the Injection refactory

        if (activity != null) {
            this.careerDAO = new CareerDAOImpl(activity);
        }

        for (Career career : careers) {

            if (!this.careerDAO.exist(career.getCareerType(), career.getPosition())) {

                if (!this.careerDAO.exist(career.getUuid())) {
                    this.careerDAO.create(career);
                }
            }
        }

        this.careerDAO.close();
    }
}
