package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.AlertListner;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.MentorshipAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.SettingDAO;
import mz.org.fgh.mentoring.dialog.AlertDialogManager;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.service.SessionService;
import mz.org.fgh.mentoring.service.SyncService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

public class ListMentorshipActivity extends BaseAuthenticateActivity implements View.OnClickListener {

    @BindView(R.id.mentorship_list)
    ListView mentorshipListView;

    @Inject
    SessionService sessionService;

    @Inject
    @Named("sessions")
    SyncService syncService;

    @Inject
    SettingDAO settingDAO;

    private boolean isActionMode;

    private ActionMode actionMode;

    private List<View> selectecdItems;
    private ArrayList<String> selectecdItemsUuid = new ArrayList<String>();;

    List<Session> sessions;

    private AlertDialogManager dialogManager;

    public static boolean active = false;

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

        active=true;
        setMentorships();
    }

    public void setMentorships() {
        sessions = sessionService.findAllSessions();
        MentorshipAdapter adapter = new MentorshipAdapter(this, sessions, settingDAO);

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

                dialogManager.showAlert(getString(R.string.sync_confirmation), new AlertListner() {
                    @Override
                    public void perform() {
                        syncService.setActivity(ListMentorshipActivity.this);
                        syncService.execute();
                    }
                });

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
            menuInflater.inflate(R.menu.mentoring_selected_list_menu, menu);

            actionMode = mode;
            selectecdItems = new ArrayList<>();

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.delete_mentorships:

                    dialogManager.showAlert(getString(R.string.delete_confirmation), new AlertListner() {
                        @Override
                        public void perform() {
                            deleteSessions();
                            mode.finish();
                        }
                    });

                    return true;

                case R.id.sync_mentorships:

                    dialogManager.showAlert(getString(R.string.sync_confirmation), new AlertListner() {
                        @Override
                        public void perform() {
                            syncSessions();
                            mode.finish();
                        }
                    });

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

    private void syncSessions(){
        syncService.setActivity(this);
        syncService.executeByUuids(this.selectecdItemsUuid.toString().replaceAll("\\[","'").replaceAll("\\]","'").replaceAll("\\,","','"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        active = false;
    }
}
