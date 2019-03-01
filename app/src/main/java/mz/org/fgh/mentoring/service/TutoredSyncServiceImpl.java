package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.widget.Toast;

import java.util.List;

import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.model.TutoredBeanResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by user on 17-Nov-16.
 */

public class TutoredSyncServiceImpl implements SyncService {

    private BaseActivity activity;
    private TutoredDAO tutoredDAO;


    public TutoredSyncServiceImpl(final TutoredDAO tutoredDAO) {
        this.tutoredDAO = tutoredDAO;
    }


    @Override
    public void execute() {

        MentoringApplication application = (MentoringApplication) activity.getApplication();
        Retrofit retrofit = application.getMentoringRetrofit();
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);

        List<Tutored> tutoreds = tutoredDAO.findAll();

        TutoredBeanResource tutoredBeanResource = new TutoredBeanResource();

        UserContext userContext = new UserContext();
        tutoredBeanResource.setUserContext(userContext);
        tutoredBeanResource.setTutoreds(tutoreds);

        Call<TutoredBeanResource> call = syncDataService.syncTutoreds(tutoredBeanResource);
        final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A enviar dados....", true, true);


        call.enqueue(new Callback<TutoredBeanResource>() {

                         @Override
                         public void onResponse(Call<TutoredBeanResource> call, Response<TutoredBeanResource> response) {

                             TutoredBeanResource body = response.body();

                             if (body.getTutored() != null) {
                                 Toast.makeText(activity, body.getTutored().getName() + " Ok", Toast.LENGTH_SHORT).show();
                             } else {
                                 Toast.makeText(activity, body.getTutoreds().size() + " Ok", Toast.LENGTH_SHORT).show();
                             }

                             dialog.dismiss();
                         }

                         @Override
                         public void onFailure(Call<TutoredBeanResource> call, Throwable t) {
                             dialog.dismiss();
                             Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
        );

    }

    @Override
    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }
}
