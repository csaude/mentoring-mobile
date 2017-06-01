package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.config.dao.FormDAO;
import mz.org.fgh.mentoring.config.dao.FormDAOImpl;
import mz.org.fgh.mentoring.config.dao.FormQuestionDAO;
import mz.org.fgh.mentoring.config.dao.FormQuestionDAOImpl;
import mz.org.fgh.mentoring.config.dao.QuestionDAO;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.Question;
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
    private BaseActivity activity;

    @Override
    public void execute() {

        MentoringApplication application = (MentoringApplication) activity.getApplication();
        Retrofit retrofit = application.getRetrofit();
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);
        Call<GenericWrapper> call = syncDataService.formQuestions();

        final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A receber dados....", true, true);

        call.enqueue(new Callback<GenericWrapper>() {

                         @Override
                         public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {

                             GenericWrapper wrapper = response.body();

                             if (wrapper == null) {
                                 Toast.makeText(activity, "Problemas com a sincronização de dados. Verifique o endereço do servidor!", Toast.LENGTH_LONG).show();
                                 return;
                             }

                             FormDAO formDAO = new FormDAOImpl(activity);
                             QuestionDAO questionDAO = new QuestionDAOImpl(activity);
                             FormQuestionDAO formQuestionDAO = new FormQuestionDAOImpl(activity);

                             for (FormQuestion formQuestion : wrapper.getFormQuestions()) {

                                 Form form = formQuestion.getForm();
                                 Question question = formQuestion.getQuestion();

                                 if (!formDAO.exist(form.getUuid())) {
                                     formDAO.create(form);
                                 }

                                 if (!questionDAO.exist(question.getUuid())) {
                                     questionDAO.create(question);
                                 }

                                 if (!formQuestionDAO.exist(formQuestion.getUuid())) {
                                     formQuestionDAO.create(formQuestion);
                                 }
                             }

                             formDAO.close();
                             questionDAO.close();
                             formQuestionDAO.close();
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
}
