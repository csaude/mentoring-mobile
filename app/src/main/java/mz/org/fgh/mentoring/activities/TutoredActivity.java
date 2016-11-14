package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Button;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.util.TutoredUtil;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public class TutoredActivity  extends AppCompatActivity {

    private Button saveTutor;
    private TutoredUtil tutoredUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutored_activity);
        tutoredUtil = new TutoredUtil(TutoredActivity.this);
        saveTutor = (Button) findViewById(R.id.save_tutored);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tutored_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
