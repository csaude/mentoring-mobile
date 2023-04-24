package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.TextView;
import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.dao.SettingDAO;
import mz.org.fgh.mentoring.config.model.Setting;
import mz.org.fgh.mentoring.process.model.Session;
import mz.org.fgh.mentoring.process.model.SessionStatus;
import mz.org.fgh.mentoring.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

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

    @BindView(R.id.tutored_name)
    TextView tutoredName;

    @BindView(R.id.process_date)
    TextView sessionDate;

    @BindView(R.id.session_submission_state)
    TextView sessionSubmissionState;

    private Context context;

    private List<Session> sessions;

    private SettingDAO settingDAO;

    public MentorshipAdapter(Context context, List<Session> sessions, SettingDAO settingDAO) {
        this.context = context;
        this.sessions = sessions;
        this.settingDAO = settingDAO;
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public int getResourceId(int position) {
        return R.layout.list_mentorship;
    }

    @Override
    public void onCreateView(int position) {
        Session session = sessions.get(position);

        Setting setting = this.settingDAO.findByDesignation("SessionLimitDate");
        if (setting != null) {
            int settingValue = setting.getValue();

            Calendar calendar = Calendar.getInstance();
            Date currentDate = new Date();
            calendar.setTime(currentDate);
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            String dateInString = settingValue + "-" + currentMonth + "-" + currentYear;
            Date sessionSubmissionLimitDate = DateUtil.parse(dateInString, DateUtil.NORMAL_PATTERN);

            if (sessionSubmissionLimitDate != null) {
                session.setSessionSubmitionState(session.isSessionAvailableToSync(sessionSubmissionLimitDate));
                sessionSubmissionState.setText((session.getSessionSubmitionState() ? context.getString(R.string.session_submission_state_valid) : context.getString(R.string.session_submission_state_expired)) + " Data da Tutoria: " + session.getPerformedDate());
                sessionSubmissionState.setTextColor(session.getSessionSubmitionState() ? Color.rgb(1, 50, 32) : Color.rgb(114, 37, 7));
            }
        }
        formName.setText(session.getForm().getName());
        sessionStatus.setText(session.getStatus().equals(SessionStatus.COMPLETE) ? context.getString(R.string.complete): context.getString(R.string.incomplete));
        tutoredName.setText(session.getTutored().getName());
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
