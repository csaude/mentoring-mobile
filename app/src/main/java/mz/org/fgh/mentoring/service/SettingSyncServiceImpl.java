package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;
import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.config.dao.SettingDAO;
import mz.org.fgh.mentoring.config.dao.SettingDAOImpl;
import mz.org.fgh.mentoring.config.model.Setting;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class SettingSyncServiceImpl implements SyncService, SettingSyncService {

    private BaseActivity activity;

    @Inject
    SettingDAO settingDAO;

    @Inject
    public SettingSyncServiceImpl(){
    }

    @Override
    public void execute() {
        MentoringApplication application = (MentoringApplication) activity.getApplication();
        Retrofit retrofit = application.getRetrofit();
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);
        Call<GenericWrapper> call = syncDataService.settings(application.getAuth().getUser().getUuid());

        final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A receber dados....", true, true);

        call.enqueue(new Callback<GenericWrapper>() {

                         @Override
                         public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {

                             GenericWrapper data = response.body();
                             if (data == null) {
                                 Toast.makeText(activity, "Problemas com a sincronização de dados. Verifique o endereço do servidor!", Toast.LENGTH_LONG).show();
                                 return;
                             }

                             List<Setting> settings = new ArrayList<>();
                             if (data.getSettings() != null && data.getSettings().size() > 0) settings.addAll(data.getSettings());
                             if (data.getSetting() != null) settings.add(data.getSetting());

                             processSettings(settings);

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
    public void processSettings(List<Setting> settings) {
        if (settings != null && !settings.isEmpty()) {
            //TODO: remove when implement Injection in all services
            if (activity != null) {
                settingDAO = new SettingDAOImpl(activity);
            }

            for (Setting setting : settings) {
                if (!settingDAO.exist(setting.getUuid())) {
                    settingDAO.create(setting);
                } else {
                    settingDAO.update(setting);
                }
            }

            settingDAO.close();
        }
    }

    @Override
    public void setActivity(BaseActivity activity) {
           this.activity = activity;
    }

    @Override
    public void executeByUuids(String uuid) {

    }
}
