package mz.org.fgh.mentoring.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.ListMentorshipActivity;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.PerformedSession;
import mz.org.fgh.mentoring.delegate.ReportDelegate;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.model.GenericWrapper;
import mz.org.fgh.mentoring.service.ReportResource;
import mz.org.fgh.mentoring.validator.TextInputLayoutValidator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PeriodFragment extends BaseFragment {

    @BindView(R.id.report_fragment_period_form_name)
    TextView formName;

    @BindView(R.id.report_fragment_period_start_date_input)
    TextInputLayout startDate;

    @BindView(R.id.report_fragment_period_end_date_input)
    TextInputLayout endDate;

    private ReportDelegate delegate;

    private TextInputLayoutValidator validator;

    @Override
    public int getResourceId() {
        return R.layout.fragment_period;
    }

    @Override
    public void onCreateView() {
        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        delegate = (ReportDelegate) getActivity();
        formName.setText(delegate.getForm().getName());

        validator = new TextInputLayoutValidator(getActivity());
        validator.addTextInputLayputs(startDate, endDate);
    }

    @OnClick(R.id.report_fragment_period_process)
    public void onClickProcess() {

        if (!validator.isValid()) {
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle(getActivity().getResources().getString(R.string.wait));
        dialog.setMessage(getActivity().getResources().getString(R.string.processing));
        dialog.show();

        Retrofit mentoringRetrofit =
                ((MentoringApplication) getActivity().getApplication()).getMentoringRetrofit();
        ReportResource reportResource = mentoringRetrofit.create(ReportResource.class);

        performRequest(dialog, reportResource);
    }

    private void performRequest(final ProgressDialog dialog, ReportResource reportResource) {
        reportResource.findPerformedSessionsByTutorAndForm(delegate.getUser().getUuid(), delegate.getForm().getUuid(),
                startDate.getEditText().getText().toString(), endDate.getEditText().getText().toString()).enqueue(new Callback<GenericWrapper>() {
            @Override
            public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {

                dialog.dismiss();

                GenericWrapper sessions = response.body();

                if (sessions == null || sessions.getPerformedSessions().isEmpty()) {
                    alertMessage(R.string.no_result, R.string.no_result_found);
                    return;
                }

                delegate.showResultFragment(sessions.getPerformedSessions());
            }

            @Override
            public void onFailure(Call<GenericWrapper> call, Throwable t) {
                dialog.dismiss();

                alertMessage(R.string.server_error, R.string.server_error_comunication);
                Log.i("Error", t.getMessage());
            }
        });
    }

    private void alertMessage(int title, int message) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getResources().getString(title))
                .setMessage(getActivity().getResources().getString(message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setCancelable(false)
                .show();
    }

    @OnClick({R.id.report_fragment_period_start_date_input, R.id.report_fragment_period_start_date_icon})
    public void onSetStartDate() {
        setDate(startDate);
    }

    @OnClick({R.id.report_fragment_period_end_date_input, R.id.report_fragment_period_end_date_icon})
    public void onSetEndtDate() {
        setDate(endDate);
    }

    private void setDate(final TextInputLayout textInputLayout) {
        Calendar instance = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = StringUtils.leftPad((dayOfMonth) + "", 2, "0") + "-" +
                        StringUtils.leftPad((month + 1) + "", 2, "0") + "-" +
                        year;

                textInputLayout.getEditText().setText(date);

                if (textInputLayout.getId() == R.id.report_fragment_period_start_date_input) {
                    delegate.setStartDate(date);
                }

                if (textInputLayout.getId() == R.id.report_fragment_period_end_date_input) {
                    delegate.setEndDate(date);
                }
            }
        }, instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }
}
