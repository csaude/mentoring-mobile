package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.activities.ConfigurationActivity;

/**
 * Created by Stélio Moiane on 11/12/16.
 */
public interface SyncService {

   void execute();

   void setActivity(ConfigurationActivity activity);
}
