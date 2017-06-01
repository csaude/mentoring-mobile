package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.service.TutorService;
import mz.org.fgh.mentoring.service.UserService;
import mz.org.fgh.mentoring.util.ServerConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView username;
    private TextView password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (TextView) findViewById(R.id.login_username);
        password = (TextView) findViewById(R.id.login_password);
        progressBar = (ProgressBar) findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.GONE);

        Button loginButton = (Button) findViewById(R.id.login_loginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (username.getText().length() == 0) {
            username.setError("Digite o utilizador!");
            return;
        }

        if (password.getText().length() == 0) {
            password.setError("Digite a Senha!");
            return;
        }

        application.setUpRetrofit(ServerConfig.ACCOUNT_MANAGER);

        UserService userService = application.getRetrofit().create(UserService.class);
        Call<UserContext> loginCall = userService.login(new UserContext(username.getText().toString(), password.getText().toString()));

        progressBar.setVisibility(View.VISIBLE);

        loginCall.enqueue(new Callback<UserContext>() {
            @Override
            public void onResponse(Call<UserContext> call, Response<UserContext> response) {
                final UserContext userContext = response.body();

                if (userContext == null) {
                    Toast.makeText(LoginActivity.this, "Problemas de Conexão com o Servidor. Por favor tente novamente!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userContext.getUsername() == null) {
                    Toast.makeText(LoginActivity.this, "Credências Inválidas!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                application.setUpRetrofit(ServerConfig.MENTORING);
                TutorService tutorService = application.getRetrofit().create(TutorService.class);

                Call<Tutor> tutorCall = tutorService.findTutorByUuid(userContext.getUuid());

                tutorCall.enqueue(new Callback<Tutor>() {
                    @Override
                    public void onResponse(Call<Tutor> call, Response<Tutor> response) {

                        Tutor tutor = response.body();
                        if (tutor.getUuid() == null) {

                            application.setUser(userContext);
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(LoginActivity.this, ConfigurationActivity.class));
                            finish();

                            return;
                        }

                        userContext.setTutor(tutor);

                        application.setUser(userContext);
                        progressBar.setVisibility(View.GONE);

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Tutor> call, Throwable t) {
                        Log.i("Error connection... ", t.getMessage());
                        Toast.makeText(LoginActivity.this, "Problemas de conexão com o servidor de Mentoria! Por favor Contacte o Administrador", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onFailure(Call<UserContext> call, Throwable t) {
                Log.i("Error connection... ", t.getMessage());
                Toast.makeText(LoginActivity.this, "Problemas de conexão com o servidor! Por favor Contacte o Administrador", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
