package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.activities.ListMentorshipActivity;
import mz.org.fgh.mentoring.config.dao.AnswerDAO;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.dto.MentorshipHelper;
import mz.org.fgh.mentoring.dto.SessionDTO;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.MentorshipBeanResource;
import mz.org.fgh.mentoring.process.model.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public class MentorshipSyncServiceImpl implements SyncService {

    @Inject
    SessionService sessionService;

    @Inject
    MentorshipService mentorshipService;

    @Inject
    AnswerDAO answerDAO;

    @Inject
    @Named("mentoring")
    Retrofit retrofit;

    private BaseActivity activity;

    @Inject
    public MentorshipSyncServiceImpl() {
    }

    @Override
    public void execute() {
        List<Session> sessions = sessionService.findAllSessions();

        if (sessions.isEmpty()) {
            Toast.makeText(activity, "Nenhum dado disponivel para sincronizar!", Toast.LENGTH_SHORT).show();
            return;
        }

        MentorshipBeanResource mentorshipBeanResource = prepareSyncData(sessions);
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);

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
                             List<String> sessionUuids = resource.getSessionUuids();
                             sessionService.deleteSessionsByUuids(sessionUuids);

                             dialog.dismiss();

                             new AlertDialog.Builder(activity)
                                     .setTitle("Tutorias enviadas")
                                     .setMessage(sessionUuids.size() + " Processo(s) de Tutoria fora(m) sincronizado(s) com sucesso!")
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
                                     .setTitle("Tutorias não enviadas")
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

    private MentorshipBeanResource prepareSyncData(List<Session> sessions) {
        UserContext user = ((MentoringApplication) activity.getApplication()).getAuth().getUser();

        MentorshipBeanResource mentorshipBeanResource = new MentorshipBeanResource();
        mentorshipBeanResource.setUserContext(user);
        Tutor tutor = new Tutor();
        tutor.setUuid(user.getUuid());

        for (Session session : sessions) {
            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setSession(session);

            List<Mentorship> mentorships = mentorshipService.findMentorshipsBySession(session);

            for (Mentorship mentorship : mentorships) {
                MentorshipHelper mentorshipHelper = new MentorshipHelper();
                mentorship.setTutor(tutor);
                mentorshipHelper.setMentorship(mentorship);

                List<Answer> answers = answerDAO.findByMentorshipUuid(mentorship.getUuid());
                mentorshipHelper.prepareAnswerHelper(answers);
                sessionDTO.addMentoshipHelper(mentorshipHelper);
            }

            mentorshipBeanResource.addSessions(sessionDTO);
        }

        return mentorshipBeanResource;
    }

    @Override
    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }
}
