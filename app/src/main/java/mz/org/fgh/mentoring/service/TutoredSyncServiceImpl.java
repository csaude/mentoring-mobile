package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.dialog.AlertDialogManager;
import mz.org.fgh.mentoring.dialog.ProgressDialogManager;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.model.LifeCycleStatus;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.model.TutoredBeanResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by user on 17-Nov-16.
 */

public class TutoredSyncServiceImpl implements TutoredSyncService {

    private BaseActivity activity;

    @Inject
    TutoredService tutoredService;

    @Inject
    @Named("mentoring")
    Retrofit retrofit;


    @Inject
    public TutoredSyncServiceImpl() {
    }

    @Override
    public void syncTutoreds(UserContext userContext, List<Tutored> tutoreds) {

        SyncDataService syncDataService = retrofit.create(SyncDataService.class);

        TutoredBeanResource tutoredBeanResource = new TutoredBeanResource();
        tutoredBeanResource.setUserContext(userContext);
        tutoredBeanResource.setTutoreds(tutoreds);

        Call<TutoredBeanResource> call = syncDataService.syncTutoreds(tutoredBeanResource);
        ProgressDialogManager progressDialogManager = new ProgressDialogManager(activity);
        final ProgressDialog dialog = progressDialogManager.getProgressBar(activity.getString(R.string.wait), activity.getString(R.string.processing));
        dialog.show();

        final AlertDialogManager alertDialogManager = new AlertDialogManager(activity);

        call.enqueue(new Callback<TutoredBeanResource>() {

                         @Override
                         public void onResponse(Call<TutoredBeanResource> call, Response<TutoredBeanResource> response) {
                             TutoredBeanResource body = response.body();

                             List<String> uuids = body.getTutoredsUuids();
                             tutoredService.deleteTutoredsByUuid(uuids);

                             dialog.dismiss();
                             alertDialogManager.showSuccessAlert(activity.getString(R.string.tudoreds_synced_with_success));
                         }

                         @Override
                         public void onFailure(Call<TutoredBeanResource> call, Throwable t) {
                             dialog.dismiss();
                             alertDialogManager.showAlert(activity.getString(R.string.server_error_comunication));

                             Log.e("TUTOREDS", t.getMessage());
                         }
                     }
        );
    }

    @Override
    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }
}
