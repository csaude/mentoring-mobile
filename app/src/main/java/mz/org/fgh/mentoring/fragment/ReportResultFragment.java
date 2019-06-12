package mz.org.fgh.mentoring.fragment;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.ReportAdapter;
import mz.org.fgh.mentoring.delegate.ReportDelegate;
import retrofit2.Retrofit;

public class ReportResultFragment extends BaseFragment {

    @BindView(R.id.report_fragment_report_result_form_name)
    TextView formName;

    @BindView(R.id.report_fragment_report_result_period)
    TextView period;

    @BindView(R.id.report_fragment_report_total_performed_value)
    TextView totalPerformed;

    @BindView(R.id.repor_fragment_report_result_list)
    RecyclerView resultList;

    @Inject
    @Named("mentoring")
    Retrofit mentoringService;

    @Override
    public int getResourceId() {
        return R.layout.fragment_period_report_result;
    }

    @Override
    public void onCreateView() {
        ReportDelegate delegate = (ReportDelegate) getActivity();

        period.setText("De " + delegate.getStartDate() + " à " + delegate.getEndDate());

        totalPerformed.setText(delegate.getTotalPerformed() + "");

        ReportAdapter adapter = new ReportAdapter(getActivity(), delegate.getPerformedSessions());
        resultList.addItemDecoration(new DividerItemDecoration(resultList.getContext(), DividerItemDecoration.VERTICAL));
        resultList.setAdapter(adapter);
    }
}