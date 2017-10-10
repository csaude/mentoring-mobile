package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.TutoredItemAdapter;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.config.dao.TutoredDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;


public class ListTutoredActivity extends BaseAuthenticateActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private ListView tutoredList;
    private Button newTutoredBtn;
    private TutoredDAO tutoredDAO;
    private TutoredItemAdapter adapter;
    private List<Tutored> tutoreds;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.list_tutoreds);

        tutoredList = (ListView) findViewById(R.id.tutored_list);
        newTutoredBtn = (Button) findViewById(R.id.new_tutored);

        tutoredDAO = new TutoredDAOImpl(this);
        tutoreds = tutoredDAO.findAll();

        adapter = new TutoredItemAdapter(this, tutoreds);
        tutoredList.setAdapter(adapter);

        tutoredList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        newTutoredBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tutored_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, TutoredActivity.class));
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        List<Tutored> newTutoreds = new ArrayList<>();

        for (Tutored tutored : this.tutoreds) {
            if (tutored.getFullName().toLowerCase().contains(query.toLowerCase())) {
                newTutoreds.add(tutored);
            }
        }

        adapter.setFilter(newTutoreds);
        return true;
    }
}
