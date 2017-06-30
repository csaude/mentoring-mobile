package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.activities.ListMentorshipActivity;
import mz.org.fgh.mentoring.config.dao.AnswerDAO;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.helpers.MentorshipHelper;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.process.dao.MentorshipDAO;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.MentorshipBeanResource;
import mz.org.fgh.mentoring.util.DateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public class MentorshipSyncServiceImpl implements SyncService {

    private BaseActivity activity;
    private MentorshipDAO mentorshipDAO;
    private AnswerDAO answerDAO;

    public MentorshipSyncServiceImpl(final MentorshipDAO mentorshipDAO, final AnswerDAO answerDAO) {
        this.mentorshipDAO = mentorshipDAO;
        this.answerDAO = answerDAO;
    }

    @Override
    public void execute() {

        MentoringApplication application = (MentoringApplication) activity.getApplication();
        Retrofit retrofit = application.getRetrofit();
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);

        List<Mentorship> mentorships = mentorshipDAO.findAll();

        if (mentorships.isEmpty()) {
            Toast.makeText(activity, "Nenhum dado disponivel para sincronizar!", Toast.LENGTH_SHORT).show();
            return;
        }

        List<MentorshipHelper> mentorshipHelpers = new ArrayList<>();

        UserContext user = ((MentoringApplication) activity.getApplication()).getAuth().getUser();

        for (Mentorship mentorship : mentorships) {
            mentorship.setTutor(user.getTutor());
            List<Answer> answers = answerDAO.findByMentorshipUuid(mentorship.getUuid());
            MentorshipHelper mentorshipHelper = new MentorshipHelper();
            mentorshipHelper.setMentorship(mentorship);
            mentorshipHelper.setStartDate(DateUtil.format(mentorship.getStartDate()));
            mentorshipHelper.setEndDate(DateUtil.format(mentorship.getEndDate()));
            mentorshipHelper.setPerformedDate(DateUtil.format(mentorship.getPerformedDate(), DateUtil.NORMAL_PATTERN));
            mentorshipHelper.prepareAnswerHelper(answers);
            mentorshipHelpers.add(mentorshipHelper);
        }

        MentorshipBeanResource mentorshipBeanResource = new MentorshipBeanResource();

        mentorshipBeanResource.setUserContext(user);
        mentorshipBeanResource.setMentorships(mentorshipHelpers);

        Call<MentorshipBeanResource> call = syncDataService.syncMentorships(mentorshipBeanResource);
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCancelable(false);
        dialog.setTitle("Aguarde");
        dialog.setMessage("A enviar dados....");
        dialog.show();

        call.enqueue(new Callback<MentorshipBeanResource>() {
                         @Override
                         public void onResponse(Call<MentorshipBeanResource> request, Response<MentorshipBeanResource> response) {
                             MentorshipBeanResource resource = response.body();
                             List<String> mentorships = resource.getMentorshipUuids();

                             answerDAO.deleteByMentorshipUuids(mentorships);
                             mentorshipDAO.deleteByUuids(mentorships);

                             dialog.dismiss();

                             new AlertDialog.Builder(activity)
                                     .setTitle("Processos Enviados")
                                     .setMessage(mentorships.size() + " Processo(s) de Mentoria fora(m) sincronizado(s) com sucesso!")
                                     .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                         public void onClick(DialogInterface dialog, int which) {
                                             ((ListMentorshipActivity) activity).setMentorships();
                                         }
                                     })
                                     .setIcon(android.R.drawable.ic_dialog_info)
                                     .setCancelable(false)
                                     .show();
                         }

                         @Override
                         public void onFailure(Call<MentorshipBeanResource> call, Throwable t) {
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
    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }
}
