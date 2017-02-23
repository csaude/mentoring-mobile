package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.widget.Toast;

import mz.org.fgh.mentoring.activities.BaseAuthenticateActivity;
import mz.org.fgh.mentoring.activities.ConfigurationActivity;
import mz.org.fgh.mentoring.config.dao.FormDAO;
import mz.org.fgh.mentoring.config.dao.FormDAOImpl;
import mz.org.fgh.mentoring.config.dao.FormQuestionDAO;
import mz.org.fgh.mentoring.config.dao.FormQuestionDAOImpl;
import mz.org.fgh.mentoring.config.dao.QuestionDAO;
import mz.org.fgh.mentoring.config.dao.QuestionDAOImpl;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.Question;
import mz.org.fgh.mentoring.helpers.FormQuestionHelper;
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

                             FormDAO formDAO = new FormDAOImpl(activity);
                             QuestionDAO questionDAO = new QuestionDAOImpl(activity);
                             FormQuestionDAO formQuestionDAO = new FormQuestionDAOImpl(activity);

                             for (FormQuestionHelper formQuestionHelper : wrapper.getFormQuestions()) {

                                 Form form = formQuestionHelper.getForm().getForm();
                                 Question question = formQuestionHelper.getQuestion();
                                 FormQuestion formQuestion = new FormQuestion(form.getCode(), question.getCode());

                                 if (!formDAO.exist(form.getCode())) {
                                     formDAO.create(form);
                                 }

                                 if (!questionDAO.exist(question.getCode())) {
                                     questionDAO.create(question);
                                 }

                                 formQuestionDAO.create(formQuestion);

                             }

                             Toast.makeText(activity, formDAO.findAll().size() + " Formularios foram sincronizadas com sucesso!", Toast.LENGTH_SHORT).show();

                             dialog.dismiss();

                             formDAO.close();
                             questionDAO.close();
                             formQuestionDAO.close();
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
    public void setActivity(BaseAuthenticateActivity activity) {
        this.activity = (ConfigurationActivity) activity;
    }
}
