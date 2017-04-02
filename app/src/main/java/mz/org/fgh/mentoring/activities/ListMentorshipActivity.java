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

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.MentorshipAdapter;
import mz.org.fgh.mentoring.config.dao.AnswerDAOImpl;
import mz.org.fgh.mentoring.process.dao.MentorshipDAOImpl;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.service.MentorshipSyncServiceImpl;
import mz.org.fgh.mentoring.service.SyncService;
import mz.org.fgh.mentoring.util.DateUtil;

public class ListMentorshipActivity extends BaseAuthenticateActivity implements View.OnClickListener {

    private Button newMentorshipBtn;

    @Override
    protected void onMentoringCreate(Bundle bundle) {

        setContentView(R.layout.activity_list_mentorship);

        newMentorshipBtn = (Button) findViewById(R.id.new_mentorship);
        ListView mentorshipListView = (ListView) findViewById(R.id.mentorship_list);

        List<Mentorship> mentorships = new MentorshipDAOImpl(this).findAll();

        MentorshipAdapter adapter = new MentorshipAdapter(this, mentorships);
        mentorshipListView.setAdapter(adapter);

        newMentorshipBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MentoringActivity.class);
        intent.putExtra("startDate", DateUtil.format(new Date()));
        startActivity(intent);
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
                SyncService syncService = new MentorshipSyncServiceImpl(new MentorshipDAOImpl(this), new AnswerDAOImpl(this));
                syncService.setActivity(this);
                syncService.execute();
                break;
        }

        return true;
    }
}
