package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mz.org.fgh.mentoring.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView usernane;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernane = (TextView) findViewById(R.id.login_username);
        password = (TextView) findViewById(R.id.login_password);

        Button loginButton = (Button) findViewById(R.id.login_loginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Logado com Sucesso! !" + usernane.getText(), Toast.LENGTH_SHORT).show();
    }
}
