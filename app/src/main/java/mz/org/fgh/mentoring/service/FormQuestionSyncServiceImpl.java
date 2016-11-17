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
 * Created by Stélio Moiane on 11/16/16.
 */
public class FormQuestionSyncServiceImpl implements SyncService {
    private ConfigurationActivity activity;

    @Override
    public void execute() {

        MentoringApplication application = (MentoringApplication) activity.getApplication();
        Retrofit retrofit = application.getRetrofit();
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);
        Call<GenericWrapper> call = syncDataService.formQuestions();
        final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A receber dados...", true, true);

        call.enqueue(new Callback<GenericWrapper>() {

                         @Override
                         public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {

                             GenericWrapper wrapper = response.body();

                             Toast.makeText(activity, wrapper.getFormQuestions().size() + " Formularios foram sincronizadas com sucesso!", Toast.LENGTH_SHORT).show();

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
