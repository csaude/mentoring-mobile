package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.AlertListner;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.MentorshipAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.AnswerDAOImpl;
import mz.org.fgh.mentoring.dialog.AlertDialogManager;
import mz.org.fgh.mentoring.model.LifeCycleStatus;
import mz.org.fgh.mentoring.model.Tutored;
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

    private boolean isActionMode;

    private ActionMode actionMode;

    private List<View> selectecdItems;
    private ArrayList<String> selectecdItemsUuid = new ArrayList<String>();;

    List<Session> sessions;

    private AlertDialogManager dialogManager;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_list_mentorship);

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(this);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        dialogManager = new AlertDialogManager(this);

        mentorshipListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        mentorshipListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toolbar.startActionMode(modeListener);
                isActionMode = true;

                toggleSelection(view, position);

                return true;
            }
        });

        mentorshipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isActionMode) {
                    toggleSelection(view, position);
                }
            }
        });

        setMentorships();
    }

    public void setMentorships() {
        sessions = sessionService.findAllSessions();
        MentorshipAdapter adapter = new MentorshipAdapter(this, sessions);

        /**
         * After reaching 5 sessions without sending to the server
         * remember tutor to send the data to the server
         */
        if(sessions.size()>=5) {
            Toast toast = Toast.makeText(this, getString(R.string.remeber_send_data_to_server), Toast.LENGTH_LONG);
            toast.show();
        }
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

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.tutored_list_menu, menu);

            actionMode = mode;
            selectecdItems = new ArrayList<>();

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.delete_tutoreds:
                    deleteSessions();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            actionMode = null;

            for (View view : selectecdItems) {
                View selectedRow = view.findViewById(R.id.selected_row);
                selectedRow.setVisibility(View.GONE);
            }

            selectecdItems = null;
            selectecdItemsUuid.clear();
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            sessions.size();
        }
    };


    public void toggleSelection(final View view, int position) {
        View selectedRow = view.findViewById(R.id.selected_row);
        Session session = sessions.get(position);

        if (View.VISIBLE == selectedRow.getVisibility()) {
            selectedRow.setVisibility(View.GONE);
            selectecdItems.remove(selectedRow);
            selectecdItemsUuid.remove(session.getUuid());

            actionMode.setTitle(selectecdItems.size() + " " + getString(R.string.itens_selected));
            return;
        }

        selectecdItems.add(selectedRow);
        selectecdItemsUuid.add(session.getUuid());
        selectedRow.setVisibility(View.VISIBLE);

        actionMode.setTitle(selectecdItems.size() + " " + getString(R.string.itens_selected));
    }

    private void deleteSessions(){
        sessionService.deleteSessionsByUuids(this.selectecdItemsUuid);
        setMentorships();
    }
}
