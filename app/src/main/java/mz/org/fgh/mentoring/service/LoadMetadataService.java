package mz.org.fgh.mentoring.service;

import android.app.ProgressDialog;

import mz.org.fgh.mentoring.activities.BaseActivity;
import mz.org.fgh.mentoring.infra.UserContext;

/**
 * Created by Stélio Moiane on 6/28/17.
 */
public interface LoadMetadataService {

    void load(final BaseActivity activity, final ProgressDialog progressDialog, final UserContext userContext);
}
