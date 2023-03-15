package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.model.GenericWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 6/28/17.
 */
public class LoadMetadataServiceImpl implements LoadMetadataService {

    @Inject
    @Named("mentoring")
    Retrofit retrofit;

    @Inject
    HealthFacilitySyncService healthFacilitySyncService;

    @Inject
    CareerSyncService careerSyncService;

    @Inject
    FormQuestionSyncService formQuestionSyncService;

    @Inject
    TutoredService tutoredService;

    @Inject
    CabinetService cabinetService;

    @Inject
    FormTargetService formTargetService;

    @Inject
    public LoadMetadataServiceImpl() {
    }

    @Override
    public void load(final BaseActivity activity, final ProgressDialog progressDialog, final UserContext userContext) {
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.wait);
        progressDialog.setMessage(progressDialog.getContext().getString(R.string.loading_metadata));
        progressDialog.show();

        SyncDataService syncDataService = retrofit.create(SyncDataService.class);

        Call<GenericWrapper> call = syncDataService.loadMetadata(userContext.getUuid());

        call.enqueue(new Callback<GenericWrapper>() {

            @Override
            public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {
                GenericWrapper data = response.body();

                if (data == null) {
                    progressDialog.cancel();
                    return;
                }

                healthFacilitySyncService.processHealthFacilities(data.getHealthFacilities());
                careerSyncService.processCarres(data.getCareers());
                if(data.getFormQuestions() != null) {
                    formQuestionSyncService.processFormQuestions(data.getFormQuestions());
                }
                tutoredService.processFoundTutoredByUser(data.getTutoreds());
                cabinetService.precessCabinets(data.getCabinets());
                formTargetService.processFormTarget(data.getFormTargets());

                if(!activity.isDestroyed()) {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericWrapper> call, Throwable t) {
                progressDialog.cancel();
                Toast.makeText(activity, "Não foi possivel carregar metadados. Tente mais tarde....", Toast.LENGTH_SHORT).show();
                Log.i("METADATA LOAD --", t.getMessage(), t);
            }
        });
    }
}