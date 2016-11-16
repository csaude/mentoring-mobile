package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.CareerDAOImpl;
import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.dao.TutoredDao;
import mz.org.fgh.mentoring.dao.TutoredDaoImpl;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.util.TutoredUtil;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public class TutoredActivity  extends BaseAuthenticateActivity {

    private TutoredUtil tutoredUtil;
    private TutoredDao tutoredDao;
    private Button takePhoto;
    private Spinner carrerSpinner;
    private Spinner positionSpinner;
    private List<Career> careers;
    private List<Career> positions;
    private CareerDAO careerDAO;
    private ArrayAdapter carrerAdapter;
    private ArrayAdapter positionAdapter;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.tutored_activity);
        tutoredUtil = new TutoredUtil(TutoredActivity.this);
        findViewById();
        getCarrer();
    }

    private void findViewById() {
        carrerSpinner = (Spinner) findViewById(R.id.tutored_carrer);
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
        switch (item.getItemId()){
            case R.id.save_tutored:
                Tutored tutored = tutoredUtil.getTutored();
                tutoredDao.create(tutored);
                tutoredDao.close();
                Toast.makeText(TutoredActivity.this, "Save Tutored", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCarrer() {

        careerDAO = new CareerDAOImpl(TutoredActivity.this);
        careers = careerDAO.findAll();
        positions = new ArrayList<>();
        carrerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, careers);
        positionAdapter  = new ArrayAdapter(this,android.R.layout.simple_spinner_item, positions);
        carrerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carrerSpinner.setAdapter(carrerAdapter);

        carrerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positions =  careerDAO.findPositionByCarrerType(adapterView.getItemAtPosition(i).toString());
                positionAdapter.clear();
                positionAdapter.addAll(positions);
                positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                positionSpinner.setAdapter(positionAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

}
