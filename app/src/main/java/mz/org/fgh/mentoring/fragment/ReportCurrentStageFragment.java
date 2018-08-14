package mz.org.fgh.mentoring.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.ReportAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.delegate.ReportDelegate;
import mz.org.fgh.mentoring.util.DateUtil;
import retrofit2.Retrofit;

public class ReportCurrentStageFragment extends BaseFragment {

    @BindView(R.id.repor_fragment_report_result_list)
    RecyclerView resultList;

    @BindView(R.id.report_fragment_report_result_form_name)
    TextView formName;

    @BindView(R.id.report_fragment_report_result_period)
    TextView period;

    @BindView(R.id.fragment_report_current_stage_target_value)
    TextView target;

    @BindView(R.id.fragment_report_current_stage_total_performed_value)
    TextView totalPerformed;

    @BindView(R.id.fragment_report_current_stage_achievement_value)
    TextView achievement;

    @Override
    public int getResourceId() {
        return R.layout.fragment_report_current_stage;
    }

    @Override
    public void onCreateView() {

        ReportDelegate delegate = (ReportDelegate) getActivity();
        formName.setText(delegate.getForm().getName());

        period.setText("De " + delegate.getStartDate() + " à " + delegate.getEndDate());

        target.setText(delegate.getTarget() + "");

        totalPerformed.setText(delegate.getPerformedSessions().size() + "");

        achievement.setText(getAchiement(delegate) + "%");

        ReportAdapter adapter = new ReportAdapter(getActivity(), delegate.getPerformedSessions());
        resultList.setAdapter(adapter);
    }

    private String getAchiement(ReportDelegate delegate) {

        BigDecimal value = new BigDecimal(delegate.getPerformedSessions().size()).multiply(new BigDecimal(100));
        value = value.divide(new BigDecimal(delegate.getTarget()), 2, BigDecimal.ROUND_CEILING);

        return (value.toString());
    }
}
