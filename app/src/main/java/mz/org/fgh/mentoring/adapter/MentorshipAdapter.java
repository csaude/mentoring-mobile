package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.process.model.SessionStatus;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public class MentorshipAdapter extends BaseAbstractAdapter {

    @BindView(R.id.mentorship_form_name)
    TextView formName;

    @BindView(R.id.session_status)
    TextView sessionStatus;

    @BindView(R.id.health_facility)
    TextView healthFacility;

    @BindView(R.id.process_date)
    TextView sessionDate;

    private Context context;

    private List<Session> sessions;

    public MentorshipAdapter(Context context, List<Session> sessions) {
        this.context = context;
        this.sessions = sessions;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public int getResourceId() {
        return R.layout.list_mentorship;
    }

    @Override
    public void onCreateView(int position) {
        Session session = sessions.get(position);

        formName.setText(session.getForm().getName());
        sessionStatus.setText(session.getStatus().equals(SessionStatus.COMPLETE) ? context.getString(R.string.complete) : context.getString(R.string.incomplete));
        healthFacility.setText(session.getHealthFacility().getHealthFacility());
        sessionDate.setText("( " + DateUtil.format(session.getCreatedAt(), DateUtil.HOURS_PATTERN) + " )");
    }

    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Session getItem(int position) {
        return this.sessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return sessions.get(position).hashCode();
    }
}
