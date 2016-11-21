package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mz.org.fgh.mentoring.helpers.TutoredHelper;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.CareerDAOImpl;
import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.CareerType;
import mz.org.fgh.mentoring.config.dao.TutoredDao;
import mz.org.fgh.mentoring.config.dao.TutoredDaoImpl;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public class TutoredActivity extends BaseAuthenticateActivity {

    private TutoredHelper tutoredHelper;
    private TutoredDao tutoredDao;
    private Button takePhoto;
    private Spinner carrerTypeSpinner;
    private Spinner positionSpinner;
    private List<Career> careers;
    private List<Career> positions;
    private CareerDAO careerDAO;
    private ArrayAdapter carrerAdapter;
    private ArrayAdapter positionAdapter;
    private Tutored tutored;
    List<CareerType> careerTypes = new ArrayList<>();

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.tutored_activity);
        tutoredHelper = new TutoredHelper(TutoredActivity.this);
        findViewById();
        getCarrer();
    }

    private void findViewById() {
        carrerTypeSpinner = (Spinner) findViewById(R.id.tutored_carrer);
        positionSpinner = (Spinner) findViewById(R.id.tutored_position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tutored_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        tutoredDao = new TutoredDaoImpl(this);
        switch (item.getItemId()) {
            case R.id.save_tutored:
                tutored = tutoredHelper.getTutored();
                tutoredDao.create(tutored);
                tutoredDao.close();
                finish();
                tutoredDao.findAll();

        }
        return super.onOptionsItemSelected(item);
    }

    private void getCarrer() {
        setSpinnerAdapter();

        carrerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                CareerType careerType = (CareerType) adapterView.getItemAtPosition(position);
                List<Career> positions = careerDAO.findPositionByCarrerType(careerType);

                ArrayAdapter adapter = new ArrayAdapter(TutoredActivity.this, android.R.layout.simple_spinner_item, positions);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                positionSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionAdapter.clear();
            }
        });
        positionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                tutored = tutoredHelper.getTutored();
                Career career = (Career) adapterView.getItemAtPosition(position);

                tutored.setCareer(career);
                tutored.setCarrerId(Long.valueOf(career.getId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                positionAdapter.clear();
            }
        });
    }

    private void setSpinnerAdapter() {
        careerDAO = new CareerDAOImpl(this);
        careerTypes.addAll(Arrays.asList(CareerType.values()));
        carrerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, careerTypes);
        carrerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carrerTypeSpinner.setAdapter(carrerAdapter);
    }

}
