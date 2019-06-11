package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.CareerType;
import mz.org.fgh.mentoring.model.LifeCycleStatus;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.validator.FieldsValidator;
import mz.org.fgh.mentoring.validator.TextViewValidator;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public class TutoredActivity extends BaseAuthenticateActivity implements FieldsValidator<Tutored> {

    @BindView(R.id.tutored_name)
    EditText tudoredName;

    @BindView(R.id.tutored_surname)
    EditText tutoredSurname;

    @BindView(R.id.tutored_phone_number)
    EditText tutoredPhoneNumber;

    @BindView(R.id.tutored_carrer)
    Spinner carrerTypeSpinner;

    @BindView(R.id.tutored_position)
    Spinner positionSpinner;

    @Inject
    TutoredDAO tutoredDAO;

    @Inject
    CareerDAO careerDAO;

    @Inject
    TextViewValidator validator;

    private Tutored tutored;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.tutored_activity);
        toolbar.setNavigationIcon(R.mipmap.ic_back);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        tutored = new Tutored();
        tutored.setLifeCycleStatus(LifeCycleStatus.ACTIVE);

        validator.addViews(tudoredName, tutoredSurname, tutoredPhoneNumber);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Arrays.asList(CareerType.values()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carrerTypeSpinner.setAdapter(adapter);
    }

    @OnItemSelected(R.id.tutored_carrer)
    public void onSelectCarrerType(int position) {

        CareerType careerType = (CareerType) carrerTypeSpinner.getItemAtPosition(position);
        List<Career> positions = careerDAO.findPositionByCarrerType(careerType);

        ArrayAdapter adapter = new ArrayAdapter(TutoredActivity.this, android.R.layout.simple_spinner_item, positions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        positionSpinner.setAdapter(adapter);
    }

    @OnItemSelected(R.id.tutored_position)
    public void onSelectPosition(int position) {
        Career career = (Career) positionSpinner.getItemAtPosition(position);
        tutored.setCareer(career);
    }

    @OnClick(R.id.save_tutored)
    public void saveTutored() {
        Tutored tutored = validate();

        if (tutored == null) {
            return;
        }

        tutoredDAO.create(tutored);
        tutoredDAO.close();

        startActivity(new Intent(this, ListTutoredActivity.class));
        finish();
    }

    @Override
    public Tutored validate() {

        if (!validator.isValid()) {
            return null;
        }

        //validate MZ phone number
        Pattern pattern = Pattern.compile("^\\+\\d{12}$");
        Matcher matcher = pattern.matcher(tutoredPhoneNumber.getText().toString());

        if (!matcher.find()) {
            tutoredPhoneNumber.setError(getString(R.string.phone_number_invalid));
            tutoredPhoneNumber.requestFocus();
            return null;
        }

        tutored.setName(tudoredName.getText().toString());
        tutored.setSurname(tutoredSurname.getText().toString());
        tutored.setPhoneNumber(tutoredPhoneNumber.getText().toString());

        return tutored;
    }
}
