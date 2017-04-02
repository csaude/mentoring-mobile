package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;
import android.widget.Toast;

import java.util.List;

import mz.org.fgh.mentoring.activities.BaseAuthenticateActivity;
import mz.org.fgh.mentoring.config.dao.AnswerDAO;
import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.model.UserContext;
import mz.org.fgh.mentoring.process.dao.MentorshipDAO;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.MentorshipBeanResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public class MentorshipSyncServiceImpl implements SyncService {

    private BaseAuthenticateActivity activity;
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

        for (Mentorship mentorship : mentorships) {
            List<Answer> answers = answerDAO.findByMentorshipUuid(mentorship.getUuid());
            mentorship.setAnswers(answers);
        }

        MentorshipBeanResource mentorshipBeanResource = new MentorshipBeanResource();

        UserContext userContext = new UserContext();
        userContext.setId(1L);
        mentorshipBeanResource.setUserContext(userContext);
        mentorshipBeanResource.setMentorships(mentorships);

        Call<MentorshipBeanResource> call = syncDataService.syncMentorships(mentorshipBeanResource);
        final ProgressDialog dialog = ProgressDialog.show(activity, "Aguarde", "A enviar dados....", true, true);


        call.enqueue(new Callback<MentorshipBeanResource>() {

                         @Override
                         public void onResponse(Call<MentorshipBeanResource> request, Response<MentorshipBeanResource> response) {
                             MentorshipBeanResource body = response.body();
                             dialog.dismiss();
                         }

                         @Override
                         public void onFailure(Call<MentorshipBeanResource> call, Throwable t) {
                             dialog.dismiss();
                             Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
        );

    }

    @Override
    public void setActivity(BaseAuthenticateActivity activity) {
        this.activity = activity;
    }
}
