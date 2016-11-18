package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.TutoredItemAdapter;
import mz.org.fgh.mentoring.dao.TutoredDao;
import mz.org.fgh.mentoring.dao.TutoredDaoImpl;
import mz.org.fgh.mentoring.model.Tutored;


/**
 * Created by Eusebio Jose Maposse on 14-Nov-16.
 */

public class ListTutoredActivity  extends BaseAuthenticateActivity  implements SearchView.OnQueryTextListener{

    private ListView tutoredList;
    private Button newTutored;
    private SearchView searchView;
    TutoredItemAdapter tutoredItemAdapter;
    private List<Tutored> tutoreds;
    private TutoredDao tutoredDao;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.list_tutoreds);
        findViewById();
        findAllTutored();
        tutoredItemAdapter = new TutoredItemAdapter(ListTutoredActivity.this,tutoreds);
        tutoredList.setAdapter(tutoredItemAdapter);
        getTutored();
        goTutoredForm();
    }

    private void findViewById() {
        tutoredList = (ListView) findViewById(R.id.tutored_list);
        newTutored = (Button) findViewById(R.id.new_tutored);
        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        searchView.setOnQueryTextListener(ListTutoredActivity.this);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tutored_list_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        findAllTutored();
    }


    private void getTutored() {
        tutoredDao.close();
        this.tutoredList.setAdapter(tutoredItemAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        tutoredItemAdapter.filter(newText);
        return false;
    }

    private void findAllTutored() {
        tutoredDao = new TutoredDaoImpl(this);
        tutoreds = tutoredDao.findAll();
        tutoredItemAdapter = new TutoredItemAdapter(ListTutoredActivity.this,tutoreds);
        tutoredList.setAdapter(tutoredItemAdapter);

    }
}
