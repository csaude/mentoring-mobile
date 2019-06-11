package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.TutoredItemAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.dialog.AlertDialogManager;
import mz.org.fgh.mentoring.model.LifeCycleStatus;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.service.SessionService;
import mz.org.fgh.mentoring.service.TutoredService;
import mz.org.fgh.mentoring.service.TutoredSyncService;


public class ListTutoredActivity extends BaseAuthenticateActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    @Inject
    TutoredService tutoredService;

    @Inject
    SessionService sessionService;

    @Inject
    TutoredSyncService tutoredSyncService;

    private ListView tutoredList;

    private Button newTutoredBtn;

    private TutoredItemAdapter adapter;

    private List<Tutored> tutoreds;

    private boolean isActionMode;

    private ActionMode actionMode;

    private List<View> selectecdItems;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.list_tutoreds);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        tutoredList = findViewById(R.id.tutored_list);
        newTutoredBtn = findViewById(R.id.new_tutored);

        tutoreds = tutoredService.findTutoredsByLifeCycleStatus(LifeCycleStatus.ACTIVE);

        adapter = new TutoredItemAdapter(this, tutoreds);
        tutoredList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        tutoredList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toolbar.startActionMode(modeListener);
                isActionMode = true;

                toggleSelection(view, position);

                return true;
            }
        });

        tutoredList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isActionMode) {
                    toggleSelection(view, position);
                }
            }
        });

        tutoredList.setAdapter(adapter);

        newTutoredBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tutored_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.tutored_menu_sync:

                List<Session> sessions = sessionService.findAllSessions();

                if (!sessions.isEmpty()) {
                    AlertDialogManager alertDialogManager = new AlertDialogManager(this);
                    alertDialogManager.showAlert(getString(R.string.all_sessions_must_be_sync));
                    return true;
                }

                List<Tutored> tutoreds = tutoredService.findTutoredsByLifeCycleStatus(LifeCycleStatus.INACTIVE);

                if (tutoreds.isEmpty()) {
                    AlertDialogManager alertDialogManager = new AlertDialogManager(this);
                    alertDialogManager.showSuccessAlert(getString(R.string.no_data_to_sync));
                    return true;
                }

                tutoredSyncService.setActivity(this);
                tutoredSyncService.syncTutoreds(application.getAuth().getUser(), tutoreds);
                break;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, TutoredActivity.class));
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        List<Tutored> newTutoreds = new ArrayList<>();

        for (Tutored tutored : this.tutoreds) {
            if (tutored.getFullName().toLowerCase().contains(query.toLowerCase())) {
                newTutoreds.add(tutored);
            }
        }

        adapter.setFilter(newTutoreds);
        return true;
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
                    deleteTutoreds();
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
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            tutoreds.size();
        }
    };

    private void deleteTutoreds() {

        for (Tutored tutored : tutoreds) {
            if (LifeCycleStatus.INACTIVE.equals(tutored.getLifeCycleStatus())) {
                tutoredService.updateTutored(tutored);
            }
        }

        adapter.setFilter(tutoredService.findTutoredsByLifeCycleStatus(LifeCycleStatus.ACTIVE));
    }

    public void toggleSelection(final View view, int position) {
        View selectedRow = view.findViewById(R.id.selected_row);
        Tutored tutored = tutoreds.get(position);

        if (View.VISIBLE == selectedRow.getVisibility()) {
            tutored.setLifeCycleStatus(LifeCycleStatus.ACTIVE);
            selectedRow.setVisibility(View.GONE);
            selectecdItems.remove(selectedRow);

            actionMode.setTitle(selectecdItems.size() + " " + getString(R.string.itens_selected));
            return;
        }

        selectecdItems.add(selectedRow);
        tutored.setLifeCycleStatus(LifeCycleStatus.INACTIVE);
        selectedRow.setVisibility(View.VISIBLE);

        actionMode.setTitle(selectecdItems.size() + " " + getString(R.string.itens_selected));
    }
}
