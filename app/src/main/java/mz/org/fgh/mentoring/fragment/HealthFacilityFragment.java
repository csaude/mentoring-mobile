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
import mz.org.fgh.mentoring.config.model.District;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.event.HealthFacilityEvent;
import mz.org.fgh.mentoring.event.MessageEvent;
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

    @Inject
    DistrictDAO districtDAO;

    @Inject
    HealthFacilityDAO healthFacilityDAO;

    @Inject
    EventBus eventBus;

    private List<District> districts;
    private List<HealthFacility> healthFacilities;
    private List<HealthFacility> healthFacilitiesPerDistrict;

    private boolean facilitySelected = false;

    private HealthFacility healthFacility;

    @Override
    public int getResourceId() {
        return R.layout.fragment_health_facility;
    }

    @Override
    public void onCreateView() {

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        districts = districtDAO.findAll();
        healthFacilities = healthFacilityDAO.findAll();

        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getProvinces(districts));
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);
    }

    @OnClick(R.id.fragment_date_picker)
    public void onclickDatePicker() {

        Calendar instance = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this,
                instance.get(Calendar.YEAR),
                instance.get(Calendar.MONTH),
                instance.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @OnItemSelected(R.id.fragment_province)
    public void onSelectProvince() {
        ArrayAdapter<District> districtAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, districts);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtAdapter);
    }

    @OnItemSelected(R.id.fragment_distric)
    public void onSelectDistrict(int position) {

        District district = districts.get(position);

        healthFacilitiesPerDistrict = getHealthFacilities(healthFacilities, district.getUuid());
        ArrayAdapter<HealthFacility> healthFacilityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, healthFacilitiesPerDistrict);
        healthFacilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        healthFacilitySpinner.setAdapter(healthFacilityAdapter);
    }

    @OnItemSelected(R.id.fragment_health_facility)
    public void onSelectHealthFacility(int position) {

        if (!facilitySelected) {
            return;
        }

        healthFacility = healthFacilitiesPerDistrict.get(position);
        eventBus.post(new HealthFacilityEvent(healthFacility));
    }

    @OnTouch(R.id.fragment_health_facility)
    public boolean onTouch(View view) {
        facilitySelected = true;
        return false;
    }

    private List<String> getProvinces(List<District> districts) {

        List<String> provinces = new ArrayList<>();

        for (District district : districts) {

            String province = district.getProvince();

            if (!provinces.contains(province)) {
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

    @Override
    public void validate(ViewPager viewPager, int position) {

        if (performedDate == null) {
            return;
        }

        if (performedDate.getText().toString().isEmpty()) {
            Snackbar.make(getView(), getString(R.string.performed_date_must_be_selected), Snackbar.LENGTH_SHORT).show();
            viewPager.setCurrentItem(position);
            return;
        }

        if (healthFacility == null) {
            Snackbar.make(getView(), getString(R.string.health_facility_must_be_selected), Snackbar.LENGTH_SHORT).show();
            viewPager.setCurrentItem(position);
        }
    }
}