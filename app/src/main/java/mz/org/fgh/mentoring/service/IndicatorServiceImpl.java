package mz.org.fgh.mentoring.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import mz.org.fgh.mentoring.config.dao.AnswerDAO;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.event.EventType;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.dto.IndicatorHelper;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.process.dao.IndicatorDAO;
import mz.org.fgh.mentoring.process.model.Indicator;
import mz.org.fgh.mentoring.process.model.IndicatorBeanResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by steliomo on 11/3/17.
 */

public class IndicatorServiceImpl implements IndicatorService {

    @Inject
    @Named("mentoring")
    Retrofit retrofit;

    @Inject
    IndicatorDAO indicatorDAO;

    @Inject
    AnswerDAO answerDAO;

    @Inject
    EventBus eventBus;

    private Activity activity;

    @Inject
    public IndicatorServiceImpl() {
    }

    @Override
    public void syncIndicators(final UserContext userContext) {
        final List<Indicator> indicators = indicatorDAO.findAll();

        if (indicators.isEmpty()) {
            Toast.makeText(activity, "Nenhum dado disponivel para sincronizar!", Toast.LENGTH_SHORT).show();
            return;
        }

        List<IndicatorHelper> indicatorHelpers = new ArrayList<>();

        for (Indicator indicator : indicators) {
            IndicatorHelper indicatorHelper = new IndicatorHelper();
            indicatorHelper.setIndicator(indicator);
            List<Answer> answers = answerDAO.findByIndicatorUuid(indicator.getUuid());
            indicatorHelper.prepareAnswerHelpers(answers);
            indicatorHelpers.add(indicatorHelper);
        }

        IndicatorBeanResource indicatorBeanResource = new IndicatorBeanResource();
        indicatorBeanResource.setUserContext(userContext);
        indicatorBeanResource.setIndicators(indicatorHelpers);

        IndicatorServiceResource indicatorServiceResource = retrofit.create(IndicatorServiceResource.class);
        Call<IndicatorBeanResource> call = indicatorServiceResource.syncIndicators(indicatorBeanResource);

        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCancelable(false);
        dialog.setTitle("Aguarde");
        dialog.setMessage("A enviar dados....");
        dialog.show();

        call.enqueue(new Callback<IndicatorBeanResource>() {
                         @Override
                         public void onResponse(Call<IndicatorBeanResource> request, Response<IndicatorBeanResource> response) {
                             IndicatorBeanResource resource = response.body();
                             List<String> indicatorsUuids = resource.getIndicatorsUuids();

                             answerDAO.deleteByIndicatorUuids(indicatorsUuids);
                             indicatorDAO.delete(indicatorsUuids);

                             dialog.dismiss();

                             new AlertDialog.Builder(activity)
                                     .setTitle("Processos Enviados")
                                     .setMessage(indicatorsUuids.size() + " Processo(s) de Indicadores fora(m) sincronizado(s) com sucesso!")
                                     .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                         public void onClick(DialogInterface dialog, int which) {
                                             eventBus.post(new ProcessEvent(EventType.SUBMIT));
                                         }
                                     })
                                     .setIcon(android.R.drawable.ic_dialog_info)
                                     .setCancelable(false)
                                     .show();
                         }

                         @Override
                         public void onFailure(Call<IndicatorBeanResource> call, Throwable t) {
                             dialog.dismiss();
                             new AlertDialog.Builder(activity)
                                     .setTitle("Processos Enviados")
                                     .setMessage("Errors ao sincronizar por favor tente novamente")
                                     .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int which) {

                                         }
                                     })
                                     .setIcon(android.R.drawable.ic_dialog_info)
                                     .setCancelable(false)
                                     .show();
                         }
                     }
        );

    }

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
