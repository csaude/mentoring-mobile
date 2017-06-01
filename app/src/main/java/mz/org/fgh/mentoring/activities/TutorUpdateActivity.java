package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.CareerDAOImpl;
import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.config.model.CareerType;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.model.TutorBeanResource;
import mz.org.fgh.mentoring.service.TutorService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TutorUpdateActivity extends BaseAuthenticateActivity implements View.OnClickListener {

    private Button tutorSubmit;
    private UserContext user;
    private EditText tutorPhoneNumber;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_tutor_update);

        user = application.getAuth().getUser();

        TextView tutorName = (TextView) findViewById(R.id.tutor_name);
        tutorName.setText(user.getFullName());

        Spinner tutorCarrer = (Spinner) findViewById(R.id.tutor_carrer);
        final Spinner tutorPosition = (Spinner) findViewById(R.id.tutor_position);

        tutorPhoneNumber = (EditText) findViewById(R.id.tutor_phone_number);

        tutorSubmit = (Button) findViewById(R.id.submit_tutor_btn);

        setUpCareeTypeSpinner(tutorCarrer, tutorPosition);

        setUpPositionSpinner(tutorPosition);

        tutorSubmit.setOnClickListener(this);
    }

    private void setUpCareeTypeSpinner(Spinner tutorCarrer, final Spinner tutorPosition) {
        ArrayAdapter carrerTypesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, CareerType.values());
        carrerTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tutorCarrer.setAdapter(carrerTypesAdapter);

        tutorCarrer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CareerType careerType = (CareerType) parent.getItemAtPosition(position);
                CareerDAO careerDAO = new CareerDAOImpl(TutorUpdateActivity.this);
                List<Career> careers = careerDAO.findPositionByCarrerType(careerType);

                ArrayAdapter<Career> positionsAdapter = new ArrayAdapter<>(TutorUpdateActivity.this, android.R.layout.simple_spinner_dropdown_item, careers);
                positionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tutorPosition.setAdapter(positionsAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpPositionSpinner(Spinner tutorPosition) {
        tutorPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Career carrer = (Career) parent.getItemAtPosition(position);
                Tutor tutor = new Tutor();
                tutor.setCareer(carrer);
                user.setTutor(tutor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        if (tutorPhoneNumber.getText().length() == 0) {
            tutorPhoneNumber.setError("Digite o número de telefone!");
            return;
        }

        Tutor tutor = user.getTutor();
        tutor.setName(user.getTutorName());
        tutor.setSurname(user.getTutorSurname());
        tutor.setPhoneNumber(tutorPhoneNumber.getText().toString());
        tutor.setUuid(user.getUuid());

        user.setTutor(tutor);

        Retrofit retrofit = application.getRetrofit();
        TutorService tutorService = retrofit.create(TutorService.class);

        TutorBeanResource resource = new TutorBeanResource();

        resource.setTutor(tutor);
        resource.setUserContext(user);

        Call<Void> tutorServiceCall = tutorService.createTutor(resource);

        tutorServiceCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(TutorUpdateActivity.this, "Dados actualizados com sucesso!", Toast.LENGTH_SHORT).show();
                application.setUser(user);

                startActivity(new Intent(TutorUpdateActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Tutot Error...", t.getMessage());
                Toast.makeText(TutorUpdateActivity.this, "Dados não actualizados! Tente novamente!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
