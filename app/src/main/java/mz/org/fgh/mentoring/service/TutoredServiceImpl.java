package mz.org.fgh.mentoring.service;

import android.content.Context;
import android.util.Log;

import java.util.List;

import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.config.dao.TutoredDAOImpl;
import mz.org.fgh.mentoring.model.GenericWrapper;
import mz.org.fgh.mentoring.model.Tutored;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 5/31/17.
 */
public class TutoredServiceImpl {

    private Retrofit retrofit;
    private TutoredDAO tutoredDAO;

    public TutoredServiceImpl(final Context context, final Retrofit retrofit) {
        this.retrofit = retrofit;
        tutoredDAO = new TutoredDAOImpl(context);
    }

    public void processFindTutoredByUser(final String userUuid) {

        TutoredService tutoredService = retrofit.create(TutoredService.class);
        final Call<GenericWrapper> tutoredCall = tutoredService.findTutoredsByUser(userUuid);

        tutoredCall.enqueue(new Callback<GenericWrapper>() {

            @Override
            public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {
                GenericWrapper body = response.body();

                if (body == null) {
                    return;
                }

                List<Tutored> tutoreds = body.getTutoreds();

                for (Tutored tutored : tutoreds) {
                    if (!tutoredDAO.exist(tutored.getUuid())) {
                        tutoredDAO.create(tutored);
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericWrapper> call, Throwable t) {
                Log.e("Tutoreds fail  --", t.getMessage());
            }

        });
    }
}
