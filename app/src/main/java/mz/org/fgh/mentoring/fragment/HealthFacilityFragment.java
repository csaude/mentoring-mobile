package mz.org.fgh.mentoring.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.config.dao.DistrictDAO;
import mz.org.fgh.mentoring.config.dao.DistrictDAOImpl;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAO;
import mz.org.fgh.mentoring.config.dao.HealthFacilityDAOImpl;
import mz.org.fgh.mentoring.config.model.District;
import mz.org.fgh.mentoring.config.model.HealthFacility;

public class HealthFacilityFragment extends Fragment {

    private MentoringActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_health_facility, container, false);
        activity = (MentoringActivity) getActivity();

        DistrictDAO districtDAO = new DistrictDAOImpl(activity);
        final List<District> districts = districtDAO.findAll();
        districtDAO.close();

        HealthFacilityDAO healthFacilityDAO = new HealthFacilityDAOImpl(activity);
        final List<HealthFacility> healthFacilities = healthFacilityDAO.findAll();
        healthFacilityDAO.close();

        Spinner provinceSpinner = (Spinner) view.findViewById(R.id.fragment_province);
        final Spinner districtSpinner = (Spinner) view.findViewById(R.id.fragment_distric);
        final Spinner healthFacilitySpinner = (Spinner) view.findViewById(R.id.fragment_health_facility);


        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, getProvinces(districts));
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<District> districtAdapter = new ArrayAdapter<District>(activity, android.R.layout.simple_spinner_dropdown_item, districts);
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                districtSpinner.setAdapter(districtAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                District district = (District) parent.getItemAtPosition(position);
                List<HealthFacility> facilities = getHealthFacilities(healthFacilities, district.getId());

                ArrayAdapter<HealthFacility> healthFacilityAdapter = new ArrayAdapter<HealthFacility>(activity, android.R.layout.simple_spinner_dropdown_item, facilities);
                healthFacilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                healthFacilitySpinner.setAdapter(healthFacilityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        healthFacilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HealthFacility healthFacility = (HealthFacility) parent.getItemAtPosition(position);

                Bundle activityBundle = activity.getBundle();
                activityBundle.putSerializable("healthFacility", healthFacility);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
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

    private List<HealthFacility> getHealthFacilities(final List<HealthFacility> healthFacilities, final Long districId) {

        List<HealthFacility> facilities = new ArrayList<>();

        for (HealthFacility healthFacility : healthFacilities) {
            if (healthFacility.getDistrictId() == districId) {
                facilities.add(healthFacility);
            }
        }

        return facilities;
    }
}