package mz.org.fgh.mentoring.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.BaseAuthenticateActivity;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.dto.SessionReportDataDTO;
import mz.org.fgh.mentoring.event.LoginEvent;
import mz.org.fgh.mentoring.infra.MentoringApplication;
import mz.org.fgh.mentoring.model.GenericWrapper;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.service.SyncDataService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SessionReportFragment extends Fragment {
    public static final String SESSION_REPORT_DATA_NAME = "sessionReportData";
    private static final float Y_AXIS_MAX = 1;
    private static final float Y_AXIS_MIN = 0;
    private static final int Y_AXIS_LABEL_COUNT = 11;
    private static final float REQUIRED_MINIMUM = 0.8F;
    private static final float LIMIT_LINE_WIDTH = 1F;
    private static final float X_AXIS_MIN = 0F;
    private static final float BAR_WIDTH = 0.9F;
    private static final float BAR_TEXT_SIZE = 14F;
    private static final String TAG = SessionReportFragment.class.getSimpleName();

    @Inject
    EventBus eventBus;

    @Inject
    @Named("mentoring")
    Retrofit retrofit;

    @BindView(R.id.sessions_bar_chart)
    BarChart barChart;

    protected MentoringApplication application;
    private SessionReportDataDTO sessionReportDataDTO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_session_report, container, false);
        ButterKnife.bind(this, view);

        BaseAuthenticateActivity possessorActivity = (BaseAuthenticateActivity) getActivity();
        application = (MentoringApplication) possessorActivity.getApplication();
        application.getMentoringComponent().inject(this);

        eventBus.register(this);

        if(possessorActivity instanceof OnChartValueSelectedListener) {
            barChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) possessorActivity);
        }
        if(possessorActivity instanceof View.OnClickListener) {
            barChart.setOnClickListener((View.OnClickListener) possessorActivity);
        }

        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        if(sessionReportDataDTO != null) {
            ObjectMapper mapper = new ObjectMapper();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            try {
                preferences.edit().putString(SESSION_REPORT_DATA_NAME,
                        mapper.writeValueAsString(sessionReportDataDTO)).commit();
            } catch (JsonProcessingException e) {
                Log.e(TAG, "Could not serialize to json");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadReportDataAndPopulateGraph();
    }

    @Subscribe
    public void onLogin(LoginEvent loginEvent) {
        Log.d(TAG , "Loading session graph data after login");
        loadReportDataAndPopulateGraph();
    }
    
    private void populateStackedBarGraph(final SessionReportDataDTO graphData) {
        if(graphData == null) return;
        List<BarEntry> entries = graphData.getEntries();
        barChart.setDescription(null);
        barChart.setNoDataText(getActivity().getResources().getString(R.string.no_data_found));
        barChart.setNoDataTextColor(Color.RED);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setHighlightPerTapEnabled(false);
        BarDataSet set = new BarDataSet(entries, "");
        set.setValueTextSize(BAR_TEXT_SIZE);
        set.setDrawIcons(false);
        int[] barColors = new int[] {
                Color.parseColor(getString(R.color.green)),
                Color.parseColor(getString(R.color.yellow))
        };
        set.setColors(barColors);
        set.setStackLabels(new String[] {
                getResources().getString(R.string.completed),
                getResources().getString(R.string.remaining)
        });

        BarData barData = new BarData(set);
        barData.setBarWidth(BAR_WIDTH);
        barData.setValueFormatter(new BarValueFormatter(graphData.getTargets()));

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();

        leftAxis.setAxisMaximum(Y_AXIS_MAX);
        leftAxis.setAxisMinimum(Y_AXIS_MIN);
        leftAxis.setLabelCount(Y_AXIS_LABEL_COUNT, true);

        rightAxis.setAxisMaximum(Y_AXIS_MAX);
        rightAxis.setAxisMinimum(Y_AXIS_MIN);
        rightAxis.setLabelCount(Y_AXIS_LABEL_COUNT, true);


        LimitLine ll = new LimitLine(REQUIRED_MINIMUM, getResources().getString(R.string.percentage_minimum));
        ll.setLineColor(Color.RED);
        ll.setLineWidth(LIMIT_LINE_WIDTH);
        ll.setTextColor(Color.BLACK);

        leftAxis.addLimitLine(ll);
        YAxisValueFormatter yAxisValueFormatter = new YAxisValueFormatter();
        leftAxis.setValueFormatter(yAxisValueFormatter);
        rightAxis.setValueFormatter(yAxisValueFormatter);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new XAxisValueFormatter(graphData.getLabels()));
        xAxis.setAxisMaximum(entries.size());
        xAxis.setAxisMinimum(X_AXIS_MIN);
        xAxis.setLabelCount(entries.size() * 2 + 1, true);
        barChart.setData(barData);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setFitBars(true);

        LegendEntry legendEntry = createMinPercentLegendEntry("% Min", Color.RED);

        Legend legend = barChart.getLegend();
        legend.setExtra(new LegendEntry[] { legendEntry });
        barChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        barChart.invalidate();
    }

    private static LegendEntry createMinPercentLegendEntry(String label, int color) {
        LegendEntry legendEntry = new LegendEntry();
        legendEntry.label = label;
        legendEntry.form = Legend.LegendForm.LINE;
        legendEntry.formColor = color;
        legendEntry.formLineWidth = 1f;
        legendEntry.formSize = 10f;
        return legendEntry;
    }

    /**
     * Create data required to populate the graph.
     * @param mentorshipList
     * @return SessionReportDataDTO
     */
    private static SessionReportDataDTO prepareGraphData(List<Mentorship> mentorshipList) {
        if(mentorshipList == null || mentorshipList.isEmpty()) return null;

        Map<Form, Integer> countOfForms = new HashMap<>();

        for(Mentorship mentorship: mentorshipList) {
            Form curForm = mentorship.getForm();
            if(countOfForms.containsKey(curForm)) {
                countOfForms.put(curForm,countOfForms.get(curForm) + 1);
            } else {
                countOfForms.put(curForm, 1);
            }
        }

        String[] xLabels = new String[countOfForms.size()];
        int[] targets = new int[countOfForms.size()];
        int[] completed = new int[countOfForms.size()];
        int index = 0;
        for(Map.Entry<Form, Integer> countOfForm: countOfForms.entrySet()) {
            Form form = countOfForm.getKey();
            xLabels[index] = getLastWordInAString(form.getName());
            targets[index] = form.getTargetFile() + form.getTargetPatient();
            completed[index] = countOfForm.getValue();
            index++;
        }
        return new SessionReportDataDTO(xLabels, targets, completed);
    }

    private static String getLastWordInAString(String sentense) {
        String[] tokens = sentense.split("\\s+");
        return tokens[tokens.length - 1];
    }

    private void loadReportDataAndPopulateGraph() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.wait);
        progressDialog.setMessage(getResources().getString(R.string.loading_report_data));
        progressDialog.show();

        // Create options to filter the mentorships to load.
        Map<String, String> options = new HashMap<>();
        Tutor tutor = application.getAuth().getUser().getTutor();
        if(tutor != null) {
            options.put("tutor", tutor.getName());
        }
        GregorianCalendar gcStart = new GregorianCalendar();
        GregorianCalendar gcEnd = new GregorianCalendar();
        gcStart.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        options.put("performedStartDate", formatter.format(gcStart.getTime()));
        options.put("performedEndDate", formatter.format(gcEnd.getTime()));
        SyncDataService syncDataService = retrofit.create(SyncDataService.class);
        Call<GenericWrapper> call = syncDataService.fetchMentorships(options);

        call.enqueue(new Callback<GenericWrapper>() {

            @Override
            public void onResponse(Call<GenericWrapper> call, Response<GenericWrapper> response) {
                ResponseBody errorBody = response.errorBody();
                if(errorBody != null) {
                    Reader reader = errorBody.charStream();
                    StringBuilder sb = new StringBuilder();
                    int ch;
                    try {
                        while ((ch = reader.read()) != -1) {
                            sb.append((char)ch);
                        }
                        Log.e(getTag(), sb.toString());
                        if(progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.cancel();
                        }

                        loadCachedReportData();
                        if(sessionReportDataDTO == null) {
                            Toast.makeText(SessionReportFragment.this.getActivity(), R.string.could_not_load_report_data, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if(reader != null) try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    GenericWrapper body = response.body();
                    if(body != null) {
                        List<Mentorship> mentorships = response.body().getMentorships();
                        sessionReportDataDTO = prepareGraphData(mentorships);
                    }
                }

                populateStackedBarGraph(sessionReportDataDTO);
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<GenericWrapper> call, Throwable t) {
                Log.i(TAG, t.getMessage(), t);
                loadCachedReportData();
                if(sessionReportDataDTO == null) {
                    if(progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    Toast.makeText(SessionReportFragment.this.getActivity(), R.string.could_not_load_report_data, Toast.LENGTH_SHORT).show();
                } else {
                    populateStackedBarGraph(sessionReportDataDTO);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                }
            }
        });
    }

    private void loadCachedReportData() {
        Log.d(TAG, "Retrieving session report data from shared preferences");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String jsonData = preferences.getString(SESSION_REPORT_DATA_NAME, null);
        if(jsonData != null) {
            try {
                sessionReportDataDTO = new ObjectMapper().readValue(jsonData, SessionReportDataDTO.class);
            } catch (IOException e) {
                Log.e(TAG, "Could not deserialize  data from json string");
                e.printStackTrace();
            }
        }
    }

    protected class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public XAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if((int) value >= mValues.length || (int) value < 0) return "";
            if(((int) (value * 10)) % 10 == 5) return mValues[(int) value];
            return "";
        }

    }

    protected class YAxisValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return (int)(value * 100) + "%";
        }
    }

    protected class BarValueFormatter implements IValueFormatter {

        private int[] requiredForms;
        BarValueFormatter(int[] requiredForms) {
            this.requiredForms = requiredForms;
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if(value == 0) return "";
            return (int) (value * requiredForms[(int) entry.getX()]) + "";
        }
    }
}
