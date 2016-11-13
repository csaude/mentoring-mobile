package mz.org.fgh.mentoring.callback;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.ConfigurationActivity;
import mz.org.fgh.mentoring.config.model.Location;
import mz.org.fgh.mentoring.service.SyncService;

/**
 * Created by Stélio Moiane on 10/24/16.
 */
public class ConfigCallback implements ActionMode.Callback {

    private ConfigurationActivity activity;

    public ConfigCallback(ConfigurationActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.config_menu_action_mode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.config_menu_sync:

                for (Location location : activity.getSelectedItems()) {

                    SyncService syncService = location.getSyncService();
                    syncService.setActivity(activity);
                    syncService.execute();

                }

                return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        activity.clearSelection();
        activity.setActionMode(null);
    }
}
