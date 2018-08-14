package mz.org.fgh.mentoring.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.PerformedSession;
import mz.org.fgh.mentoring.delegate.ReportDelegate;
import mz.org.fgh.mentoring.model.GenericWrapper;
import mz.org.fgh.mentoring.service.ReportResource;
import mz.org.fgh.mentoring.util.DateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReportTypeFragment extends BaseFragment {

    @BindView(R.id.fragment_report_type_image_view_form_name)
    TextView formName;

    @Inject
    @Named("mentoring")
    Retrofit mentoringService;

    private ReportDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_report_type;
    }

    @Override
    public void onCreateView() {
        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        delegate = (ReportDelegate) getActivity();
        formName.setText(delegate.getForm().getName());
    }

    @OnClick(R.id.fragment_report_type_current_stage)
    public void onClickCurrentStage() {


        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle(getActivity().getResources().getString(R.string.wait));
        dialog.setMessage(getActivity().getResources().getString(R.string.processing));
        dialog.show();

        ReportResource reportResource = mentoringService.create(ReportResource.class);

        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, startDate.getActualMinimum(Calendar.DAY_OF_MONTH));

        Calendar endDate = (Calendar) startDate.clone();
        endDate.set(Calendar.DAY_OF_MONTH, endDate.getActualMaximum(Calendar.DAY_OF_MONTH));

        delegate.setStartDate(DateUtil.format(startDate.getTime(), DateUtil.NORMAL_PATTERN));
        delegate.setEndDate(DateUtil.format(endDate.getTime(), DateUtil.NORMAL_PATTERN));

        reportResource.findPerformedSessionsByTutorAndForm(delegate.getUser().getUuid(), delegate.getForm().getUuid(),
                delegate.getStartDate(), delegate.getEndDate()).enqueue(new Callback<GenericWrapper>() {
            @Override
            public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {

                dialog.dismiss();

                GenericWrapper sessions = response.body();

                if (sessions == null || sessions.getPerformedSessions().isEmpty()) {
                    alertMessage(R.string.no_result, R.string.no_result_found);
                    return;
                }

                delegate.showCurrentStageReport(sessions.getPerformedSessions());
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

    @OnClick(R.id.fragment_report_type_per_period)
    public void onClickPerPeriod() {
        delegate.showPeriodFragment();
    }
}
