package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.MentorshipAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.AnswerDAOImpl;
import mz.org.fgh.mentoring.process.dao.MentorshipDAOImpl;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.service.MentorshipSyncServiceImpl;
import mz.org.fgh.mentoring.service.SessionService;
import mz.org.fgh.mentoring.service.SyncService;
import mz.org.fgh.mentoring.util.DateUtil;

public class ListMentorshipActivity extends BaseAuthenticateActivity implements View.OnClickListener {

    @BindView(R.id.mentorship_list)
    ListView mentorshipListView;

    @Inject
    SessionService sessionService;

    @Inject
    @Named("sessions")
    SyncService syncService;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_list_mentorship);

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(this);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        setMentorships();
    }

    public void setMentorships() {
        List<Session> sessions = sessionService.findAllSessions();
        MentorshipAdapter adapter = new MentorshipAdapter(this, sessions);
        mentorshipListView.setAdapter(adapter);
    }

    @OnClick(R.id.new_mentorship)
    public void onClickNewMentorship() {
        startActivity(new Intent(this, MentoringActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mentoring_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mentoring_menu_sync:
                syncService.setActivity(this);
                syncService.execute();
                break;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
