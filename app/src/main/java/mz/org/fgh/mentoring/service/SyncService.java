package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.activities.ConfigurationActivity;

/**
 * Created by Stélio Moiane on 11/12/16.
 */
public interface SyncService {
    public void execute();

    public void setActivity(ConfigurationActivity activity);
}
