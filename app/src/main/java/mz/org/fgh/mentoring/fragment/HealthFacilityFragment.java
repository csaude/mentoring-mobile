package mz.org.fgh.mentoring.fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.xml.datatype.Duration;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import mz.org.fgh.mentoring.AlertListner;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.ListMentorshipActivity;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.DistrictDAO;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAO;
import mz.org.fgh.mentoring.config.dao.SettingDAO;
import mz.org.fgh.mentoring.config.model.Cabinet;
import mz.org.fgh.mentoring.config.model.District;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormType;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Setting;
import mz.org.fgh.mentoring.dialog.AlertDialogManager;
import mz.org.fgh.mentoring.event.CabinetEvent;
import mz.org.fgh.mentoring.event.DoorEvent;
import mz.org.fgh.mentoring.event.ErrorEvent;
import mz.org.fgh.mentoring.event.HealthFacilityEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.event.TimeEvent;
import mz.org.fgh.mentoring.event.TimeOfDayEvent;
import mz.org.fgh.mentoring.service.CabinetService;
import mz.org.fgh.mentoring.util.DateUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class HealthFacilityFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, FragmentValidator {

    @BindView(R.id.fragment_date_picker)
    ImageButton dataPicker;

    @BindView(R.id.fragment_performed_date)
    EditText performedDate;

    @BindView(R.id.fragment_performed_start_time)
    EditText startTime;

    @BindView(R.id.fragment_performed_end_time)
    EditText endTime;

    @BindView(R.id.fragment_province)
    Spinner provinceSpinner;

    @BindView(R.id.fragment_distric)
    Spinner districtSpinner;

    @BindView(R.id.fragment_health_facility)
    Spinner healthFacilitySpinner;

    @BindView(R.id.fragment_cabinet)
    Spinner cabinetSpinner;

    @BindView(R.id.fragment_cabinet_text)
    TextView cabinetTxt;

    @BindView(R.id.fragment_time_of_day)
    Spinner timeOfDaySpinner;

    @BindView(R.id.fragment_time_of_day_text)
    TextView timeOfDayTxt;

    @BindView(R.id.fragment_door)
    Spinner doorSpinner;

    @BindView(R.id.fragment_door_text)
    TextView doorTxt;

    @BindView(R.id.fragment_start_time_picker_text)
    TextView startTimeTxt;

    @BindView(R.id.fragment_end_time_picker_text)
    TextView endTimeTxt;

    @BindView(R.id.fragment_start_time_picker)
    ImageButton startTimeImgBtn;

    @BindView(R.id.fragment_end_time_picker)
    ImageButton endTimeImgBtn;

    @Inject
    DistrictDAO districtDAO;

    @Inject
    HealthFacilityDAO healthFacilityDAO;

    @Inject
    SettingDAO settingDAO;

    @Inject
    EventBus eventBus;

    @Inject
    CabinetService cabinetService;

    private List<String> provinces;
    private List<District> districts;
    private List<HealthFacility> healthFacilities;
    private List<HealthFacility> healthFacilitiesPerDistrict;

    private List<Cabinet> cabinets;

    private String province;

    private District district;

    private HealthFacility healthFacility;

    private Cabinet cabinet;

    private boolean valid;

    private TimeEvent startTimeEvent;

    private TimeEvent endTimeEvent;

    private Form form;

    private String door;

    private String timeOfDay;

    private AlertDialogManager dialogManager;

    @Override
    public int getResourceId() {
        return R.layout.fragment_health_facility;
    }

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        District district = new District();
        district.setDistrict(getString(R.string.select));

        districts = new ArrayList<>();
        districts.add(district);
        districts.addAll(districtDAO.findAll());

        healthFacilities = healthFacilityDAO.findAll();

        provinces = new ArrayList<>();
        provinces.add(getString(R.string.select));
        provinces.addAll(getProvinces(districts));

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, provinces);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);

        this.valid = false;

        configureCabinetSpinner();

        configureDoorAndTimeOfDaySpinner();
        configureCustomLabels();

        dialogManager = new AlertDialogManager(this.getActivity());
    }

    private void configureCabinetSpinner() {

        Bundle arguments = getArguments();
        form = (Form) arguments.get("form");


        cabinetSpinner.setVisibility(View.VISIBLE);
        cabinetTxt.setVisibility(View.VISIBLE);

        if (form != null && FormType.MENTORING_CUSTOM.equals(form.getFormType())) {
            cabinetSpinner.setVisibility(View.INVISIBLE);
            cabinetTxt.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * For some forms specific labels have to be displayed in different
     * word such as Cabinet, for 'Monitoria do ATS' form the Techical Team
     * prefer Sector
     */
    private void configureCustomLabels() {

        Bundle arguments = getArguments();
        form = (Form) arguments.get("form");

        if (form != null && form.getUuid().equals("122399f86199439cbbfe3deef149be87")) {
            cabinetTxt.setText("Sector:");
        }

    }

    /**
     * Display Door and Time Table only for form Monitoria do ATS
     * Hide Start and End time for the same form
     */
    private void configureDoorAndTimeOfDaySpinner() {

        Bundle arguments = getArguments();
        form = (Form) arguments.get("form");

        doorSpinner.setVisibility(View.INVISIBLE);
        doorTxt.setVisibility(View.INVISIBLE);
        timeOfDaySpinner.setVisibility(View.GONE);
        timeOfDayTxt.setVisibility(View.GONE);

        if (form != null && form.getUuid().equals("122399f86199439cbbfe3deef149be87")) {
            doorSpinner.setVisibility(View.VISIBLE);
            doorTxt.setVisibility(View.VISIBLE);
            startTime.setVisibility(View.GONE);
            endTime.setVisibility(View.GONE);
            startTimeTxt.setVisibility(View.GONE);
            endTimeTxt.setVisibility(View.GONE);
            startTimeImgBtn.setVisibility(View.GONE);
            endTimeImgBtn.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.fragment_date_picker, R.id.fragment_performed_date})
    public void onclickDatePicker() {

        Calendar instance = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                instance.get(Calendar.YEAR),
                instance.get(Calendar.MONTH),
                instance.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    @OnItemSelected(R.id.fragment_province)
    public void onSelectProvince(int position) {

        province = provinces.get(position);

        if (getResources().getString(R.string.select).equals(province)) {
            clearDropDowns();
            return;
        }

        ArrayAdapter<District> districtAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districts);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtAdapter);
    }

    private void clearDropDowns() {
        ArrayAdapter<District> districtAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<District>());
        districtSpinner.setAdapter(districtAdapter);

        healthFacility = null;
        ArrayAdapter<HealthFacility> healthFacilityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<HealthFacility>());
        healthFacilitySpinner.setAdapter(healthFacilityAdapter);

        cabinet = null;
        ArrayAdapter<Cabinet> cabinetsArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<Cabinet>());
        cabinetSpinner.setAdapter(cabinetsArrayAdapter);
    }

    @OnItemSelected(R.id.fragment_distric)
    public void onSelectDistrict(int position) {
        this.district = districts.get(position);

        healthFacilitiesPerDistrict = new ArrayList<>();
        healthFacilitiesPerDistrict.add(new HealthFacility(getString(R.string.select)));

        healthFacilitiesPerDistrict.addAll(getHealthFacilities(healthFacilities, district.getUuid()));
        ArrayAdapter<HealthFacility> healthFacilityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, healthFacilitiesPerDistrict);
        healthFacilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        healthFacilitySpinner.setAdapter(healthFacilityAdapter);
    }

    @OnItemSelected(R.id.fragment_health_facility)
    public void onSelectHealthFacility(int position) {
        healthFacility = healthFacilitiesPerDistrict.get(position);

        cabinets = new ArrayList<>();
        cabinets.add(new Cabinet(getString(R.string.select)));
        cabinets.addAll(cabinetService.findAllCabinets());

        ArrayAdapter<Cabinet> cabinetsArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cabinets);
        cabinetsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cabinetSpinner.setAdapter(cabinetsArrayAdapter);

        String[] timeOfDayOptions = { "Seleccione...","DIA", "TARDE/NOITE"};
        ArrayAdapter<String> timeOfDayArrayAdapter =new ArrayAdapter<String>(getActivity(),   android.R.layout.simple_spinner_item, timeOfDayOptions);
        timeOfDayArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeOfDaySpinner.setAdapter(timeOfDayArrayAdapter);

        String[] doorOptions = { "Seleccione...","1", "2", "3", "4"};
        ArrayAdapter<String> doorArrayAdapter =new ArrayAdapter<String>(getActivity(),   android.R.layout.simple_spinner_item, doorOptions);
        doorArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doorSpinner.setAdapter(doorArrayAdapter);

        eventBus.post(new HealthFacilityEvent(healthFacility));
    }

    private List<String> getProvinces(List<District> districts) {

        List<String> provinces = new ArrayList<>();

        for (District district : districts) {

            String province = district.getProvince();

            if (province != null && !provinces.contains(province)) {
                provinces.add(province);
            }
        }

        return provinces;
    }

    private List<HealthFacility> getHealthFacilities(final List<HealthFacility> healthFacilities, final String districtUuid) {

        List<HealthFacility> facilities = new ArrayList<>();

        for (HealthFacility healthFacility : healthFacilities) {
            if (healthFacility.getDistrict().getUuid().equals(districtUuid)) {
                facilities.add(healthFacility);
            }
        }

        return facilities;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = StringUtils.leftPad((dayOfMonth) + "", 2, "0") + "-" +
                StringUtils.leftPad((monthOfYear + 1) + "", 2, "0") + "-" +
                year;

        performedDate.setText(date);
        eventBus.post(new MessageEvent<>(date));
    }

    @OnItemSelected(R.id.fragment_cabinet)
    public void onCabinetSelected(int position) {

        this.cabinet = cabinets.get(position);

        /**
         * Display timeOfDay only for ´Monitoria do ATS´ form and
         * Cabinet 'Banco de Socorro'
         */
        if(form.getUuid().equals("122399f86199439cbbfe3deef149be87")&&this.cabinet.toString().equals("Banco de Socorro")){
            timeOfDaySpinner.setVisibility(View.VISIBLE);
            timeOfDayTxt.setVisibility(View.VISIBLE);
        }else if(form.getUuid().equals("122399f86199439cbbfe3deef149be87")&&!this.cabinet.toString().equals("Banco de Socorro")){
            timeOfDaySpinner.setVisibility(View.GONE);
            timeOfDayTxt.setVisibility(View.GONE);
        }

        eventBus.post(new CabinetEvent(this.cabinet));
    }

    @OnItemSelected(R.id.fragment_door)
    public void onDoorSelected(int position) {
        String door=position+"";
        this.door=door;
        eventBus.post(new DoorEvent<>(door));
    }

    @OnItemSelected(R.id.fragment_time_of_day)
    public void ontimeOfDaySelected(int position) {
        String timeOfDay=position+"";
        this.timeOfDay =timeOfDay;
        eventBus.post(new TimeOfDayEvent<>(timeOfDay));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (performedDate == null) {
            valid = false;
            return;
        }

        performedDate.setError(null);

        if (isEmptyPerformedDate()) {
            performedDate.setError(getString(R.string.performed_date_must_be_selected));
            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        Setting setting = this.settingDAO.findByDesignation("SessionLimitDate");

        if (setting != null) {
            Date sessionSubmissionLimitDate = setting.getValue();
            Calendar subCalendar = Calendar.getInstance();
            subCalendar.setTime(sessionSubmissionLimitDate);

            Date performedDateValue = DateUtil.parse(performedDate.getText().toString(), DateUtil.NORMAL_PATTERN);
            Calendar performeCalendar = Calendar.getInstance();
            performeCalendar.setTime(performedDateValue);

            int performedDateMonth = performeCalendar.get(Calendar.MONTH) + 1;
            int submissionDateMonth = subCalendar.get(Calendar.MONTH) + 1;
            int performedDateYear = performeCalendar.get(Calendar.YEAR);
            int submissionDateYear = subCalendar.get(Calendar.YEAR);
            boolean isValidSubmissionPeriod = sessionSubmissionLimitDate.after(performedDateValue) &&
                    (performedDateMonth == submissionDateMonth
                            && performedDateYear == submissionDateYear);
            if (!isValidSubmissionPeriod) {
                dialogManager.showAlert(getString(R.string.session_submission_state_expired_alert), new AlertListner() {

                    @Override
                    public void perform() {
                        HealthFacilityFragment.this.getActivity().finish();
                    }
                });
            }
        }
        /**
         * This validation is applicable only for forms different of 'Monitoria do ATS'
         */
        if(!form.getUuid().equals("122399f86199439cbbfe3deef149be87")) {
            startTime.setError(null);

            if (isEmptyStartTime()) {
                startTime.setError(getString(R.string.start_time_must_be_selected));
                viewPager.setCurrentItem(position);
                valid = false;
                return;
            }

            endTime.setError(null);

            if (isEmptyEndTime()) {
                endTime.setError(getString(R.string.end_time_must_be_selected));
                viewPager.setCurrentItem(position);
                valid = false;
                return;
            }

            if (IsStartTimeLowerThanEndTime()) {
                viewPager.setCurrentItem(position);
                eventBus.post(new ErrorEvent(getString(R.string.start_time_must_not_be_lower_than_end_time)));
                valid = false;
                return;
            }
        }

        if(form.getUuid().equals("122399f86199439cbbfe3deef149be87")) {
            TextView doorView = (TextView) doorSpinner.getSelectedView();
            doorView.setError(null);

            if(this.cabinet.getName().equals("Banco de Socorro")){
                TextView timeOfDayView = (TextView) timeOfDaySpinner.getSelectedView();
                timeOfDayView.setError(null);

                if (this.timeOfDay.equals("0")) {
                    timeOfDayView.setTextColor(Color.RED);
                    timeOfDayView.setError(getString(R.string.time_of_day_must_be_selected));

                    viewPager.setCurrentItem(position);
                    valid = false;
                    return;
                }
            }

            if (this.door.equals("0")) {
                doorView.setTextColor(Color.RED);
                doorView.setError(getString(R.string.door_must_be_selected));

                viewPager.setCurrentItem(position);
                valid = false;
                return;
            }

        }

        TextView provinceView = (TextView) provinceSpinner.getSelectedView();
        provinceView.setError(null);

        if (getString(R.string.select).equals(province)) {
            provinceView.setTextColor(Color.RED);
            provinceView.setError(getString(R.string.province_must_be_selected));

            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        TextView districtView = (TextView) districtSpinner.getSelectedView();
        districtView.setError(null);

        if (district == null || getString(R.string.select).equals(district.getDistrict())) {
            districtView.setTextColor(Color.RED);
            districtView.setError(getString(R.string.district_must_be_selected));

            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        TextView healthFacilityView = (TextView) healthFacilitySpinner.getSelectedView();
        healthFacilityView.setError(null);

        if (healthFacility == null || getString(R.string.select).equals(healthFacility.getHealthFacility())) {

            healthFacilityView.setTextColor(Color.RED);
            healthFacilityView.setError(getString(R.string.health_facility_must_be_selected));

            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        if (FormType.MENTORING_CUSTOM.equals(form.getFormType())) {
            valid = true;
            return;
        }

        TextView cabinetView = (TextView) cabinetSpinner.getSelectedView();
        cabinetView.setError(null);

        if (cabinet == null || getString(R.string.select).equals(cabinet.getName())) {
            cabinetView.setTextColor(Color.RED);
            cabinetView.setError(getString(R.string.cabinet_must_be_selected));

            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        valid = true;
    }

    private boolean isEmptyPerformedDate() {
        return performedDate.getText().toString().isEmpty();
    }

    private boolean isEmptyStartTime() {
        return startTime.getText().toString().isEmpty();
    }

    private boolean isEmptyEndTime() {
        return endTime.getText().toString().isEmpty();
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @OnClick({R.id.fragment_performed_start_time, R.id.fragment_start_time_picker})
    public void onClickStartTime() {

        Calendar instance = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onSetStartTimeListner(),
                instance.get(Calendar.HOUR_OF_DAY),
                instance.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));

        timePickerDialog.show();
    }

    @OnClick({R.id.fragment_performed_end_time, R.id.fragment_end_time_picker})
    public void onClickEndTime() {

        Calendar instance = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onSetEndTimeListner(),
                instance.get(Calendar.HOUR_OF_DAY),
                instance.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getActivity()));

        timePickerDialog.show();
    }

    @NonNull
    private TimePickerDialog.OnTimeSetListener onSetStartTimeListner() {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTime.setText(StringUtils.leftPad((hourOfDay) + "", 2, "0") + " : " + StringUtils.leftPad((minute) + "", 2, "0"));
                startTimeEvent = new TimeEvent(hourOfDay, minute, 0, 0);
                eventBus.post(startTimeEvent);
            }
        };
    }

    @NonNull
    private TimePickerDialog.OnTimeSetListener onSetEndTimeListner() {
        return new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTime.setText(StringUtils.leftPad((hourOfDay) + "", 2, "0") + " : " + StringUtils.leftPad((minute) + "", 2, "0"));
                endTimeEvent = new TimeEvent(0, 0, hourOfDay, minute);
                eventBus.post(endTimeEvent);
            }
        };
    }

    private boolean IsStartTimeLowerThanEndTime() {

        if (startTimeEvent == null || endTimeEvent == null) {
            return false;
        }

        Calendar startInstance = Calendar.getInstance();
        startInstance.setTime(DateUtil.parse(performedDate.getText().toString(), DateUtil.NORMAL_PATTERN));
        startInstance.set(Calendar.HOUR_OF_DAY, startTimeEvent.getStartHour());
        startInstance.set(Calendar.MINUTE, startTimeEvent.getStartMinute());
        startInstance.set(Calendar.SECOND, 0);

        Calendar endInstance = Calendar.getInstance();
        endInstance.setTime(DateUtil.parse(performedDate.getText().toString(), DateUtil.NORMAL_PATTERN));
        endInstance.set(Calendar.HOUR_OF_DAY, endTimeEvent.getEndHour());
        endInstance.set(Calendar.MINUTE, endTimeEvent.getEndMinute());
        endInstance.set(Calendar.SECOND, 0);

        return !endInstance.after(startInstance);
    }
}