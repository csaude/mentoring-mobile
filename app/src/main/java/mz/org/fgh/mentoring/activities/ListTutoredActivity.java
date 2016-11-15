package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.TutoredItemAdapter;
import mz.org.fgh.mentoring.dao.TutoredDao;
import mz.org.fgh.mentoring.dao.TutoredDaoImpl;
import mz.org.fgh.mentoring.model.Tutored;


/**
 * Created by Eusebio Jose Maposse on 14-Nov-16.
 */

public class ListTutoredActivity  extends AppCompatActivity {

    private ListView tutoredsList;
    private Button newTutored;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutored_list);
        tutoredsList = (ListView) findViewById(R.id.tutored_list);
        newTutored = (Button) findViewById(R.id.new_tutored);
        getTutoreds();
        goTutoredForm();

    }

    private void goTutoredForm() {
        newTutored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(ListTutoredActivity.this, TutoredActivity.class);
                startActivity(go);
            }
        });
    }

    private void getTutoreds() {
        TutoredDao tutoredDao = new TutoredDaoImpl(this);
        List<Tutored> tutoredList = tutoredDao.findAll();
        tutoredDao.close();
        TutoredItemAdapter tutoredItemAdapter = new TutoredItemAdapter(ListTutoredActivity.this,tutoredList);
        tutoredsList.setAdapter(tutoredItemAdapter);
    }

    public void setTutoredsList(ListView tutoredsList) {
        this.tutoredsList = tutoredsList;
    }
    public Button getNewTutored() {
        return newTutored;
    }
    public void setNewTutored(Button newTutored) {
        this.newTutored = newTutored;
    }
}
