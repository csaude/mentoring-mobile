package mz.org.fgh.mentoring.fragment;


import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTouch;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.DistrictDAO;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAO;
import mz.org.fgh.mentoring.config.model.Cabinet;
import mz.org.fgh.mentoring.config.model.District;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.event.CabinetEvent;
import mz.org.fgh.mentoring.event.HealthFacilityEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
import mz.org.fgh.mentoring.service.CabinetService;
import mz.org.fgh.mentoring.util.DateUtil;
import mz.org.fgh.mentoring.validator.FragmentValidator;

public class HealthFacilityFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, FragmentValidator {

    @BindView(R.id.fragment_date_picker)
    ImageButton dataPicker;

    @BindView(R.id.fragment_performed_date)
    EditText performedDate;

    @BindView(R.id.fragment_province)
    Spinner provinceSpinner;

    @BindView(R.id.fragment_distric)
    Spinner districtSpinner;

    @BindView(R.id.fragment_health_facility)
    Spinner healthFacilitySpinner;

    @BindView(R.id.fragment_cabinet)
    Spinner cabinetSpinner;

    @Inject
    DistrictDAO districtDAO;

    @Inject
    HealthFacilityDAO healthFacilityDAO;

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
        eventBus.post(new CabinetEvent(this.cabinet));
    }

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (performedDate == null) {
            valid = false;
            return;
        }

        if (isEmptyDate()) {
            Snackbar.make(getView(), getString(R.string.performed_date_must_be_selected), Snackbar.LENGTH_SHORT).show();
            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        if (getString(R.string.select).equals(province)) {
            Snackbar.make(getView(), getString(R.string.province_must_be_selected), Snackbar.LENGTH_SHORT).show();
            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        if (district == null || getString(R.string.select).equals(district.getDistrict())) {
            Snackbar.make(getView(), getString(R.string.district_must_be_selected), Snackbar.LENGTH_SHORT).show();
            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        if (healthFacility == null || getString(R.string.select).equals(healthFacility.getHealthFacility())) {
            Snackbar.make(getView(), getString(R.string.health_facility_must_be_selected), Snackbar.LENGTH_SHORT).show();
            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        if (cabinet == null || getString(R.string.select).equals(cabinet.getName())) {
            Snackbar.make(getView(), getString(R.string.cabinet_must_be_selected), Snackbar.LENGTH_SHORT).show();
            viewPager.setCurrentItem(position);
            valid = false;
            return;
        }

        valid = true;
    }

    private boolean isEmptyDate() {
        return performedDate.getText().toString().isEmpty();
    }

    @Override
    public boolean isValid() {
        return valid;
    }
}