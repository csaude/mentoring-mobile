package mz.org.fgh.mentoring.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.infra.UserContext;
import mz.org.fgh.mentoring.service.TutorService;
import mz.org.fgh.mentoring.service.UserServiceResource;
import mz.org.fgh.mentoring.util.ServerConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_username)
    TextView username;

    @BindView(R.id.login_password)
    TextView password;

    @Inject
    @Named("account")
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);
    }

    @OnClick(R.id.login_loginButton)
    public void onClickLogin() {

        if (username.getText().length() == 0) {
            username.setError("Digite o utilizador!");
            return;
        }

        if (password.getText().length() == 0) {
            password.setError("Digite a Senha!");
            return;
        }

        UserServiceResource userService = retrofit.create(UserServiceResource.class);
        Call<UserContext> loginCall = userService.login(new UserContext(username.getText().toString(), password.getText().toString()));

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("A Logar no sistema...");
        progressDialog.show();

        loginCall.enqueue(new Callback<UserContext>() {
            @Override
            public void onResponse(Call<UserContext> call, Response<UserContext> response) {
                final UserContext userContext = response.body();

                if (userContext == null) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity.this, "Problemas de Conexão com o Servidor. Por favor tente novamente!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userContext.getUsername() == null) {
                    progressDialog.cancel();
                    Toast.makeText(LoginActivity.this, "Credências Inválidas!", Toast.LENGTH_SHORT).show();
                    return;
                }

                application.setUpRetrofit(ServerConfig.MENTORING);
                TutorService tutorService = application.getRetrofit().create(TutorService.class);

                Call<Tutor> tutorCall = tutorService.findTutorByUuid(userContext.getUuid());

                tutorCall.enqueue(new Callback<Tutor>() {
                    @Override
                    public void onResponse(Call<Tutor> call, Response<Tutor> response) {

                        Tutor tutor = response.body();
                        userContext.setTutor(tutor);

                        application.setUser(userContext);

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        progressDialog.cancel();
                    }

                    @Override
                    public void onFailure(Call<Tutor> call, Throwable t) {
                        progressDialog.cancel();
                        Log.i("Error connection... ", t.getMessage());
                        Toast.makeText(LoginActivity.this, "Problemas de conexão com o servidor de Mentoria! Por favor Contacte o Administrador", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<UserContext> call, Throwable t) {
                progressDialog.cancel();
                Log.i("Error connection... ", t.getMessage());
                Toast.makeText(LoginActivity.this, "Problemas de conexão com o servidor! Por favor Contacte o Administrador", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
