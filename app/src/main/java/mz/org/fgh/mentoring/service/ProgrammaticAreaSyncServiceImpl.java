package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.activities.ConfigurationActivity;
import mz.org.fgh.mentoring.service.SyncService;

/**
 * Created by Stélio Moiane on 11/14/16.
 */
public class ProgrammaticAreaSyncServiceImpl implements SyncService {

    private ConfigurationActivity activity;

    @Override
    public void execute() {

    }

    @Override
    public void setActivity(ConfigurationActivity activity) {
        this.activity = activity;
    }
}
