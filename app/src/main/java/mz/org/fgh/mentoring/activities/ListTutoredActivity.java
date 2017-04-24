package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.TutoredItemAdapter;
import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.config.dao.TutoredDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.service.SyncService;
import mz.org.fgh.mentoring.service.TutoredSyncServiceImpl;


/**
 * Created by Eusebio Jose Maposse on 14-Nov-16.
 */

public class ListTutoredActivity extends BaseAuthenticateActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    private ListView tutoredList;
    private Button newTutoredBtn;
    private SearchView searchView;
    private TutoredDAO tutoredDAO;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.list_tutoreds);

        tutoredList = (ListView) findViewById(R.id.tutored_list);
        newTutoredBtn = (Button) findViewById(R.id.new_tutored);
        searchView = (SearchView) findViewById(R.id.simpleSearchView);
        searchView.setOnQueryTextListener(this);

        tutoredDAO = new TutoredDAOImpl(this);
        List<Tutored> tutoreds = tutoredDAO.findAll();

        TutoredItemAdapter adapter = new TutoredItemAdapter(this, tutoreds);
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
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.tutored_list_menu, menu);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SyncService syncService = new TutoredSyncServiceImpl(new TutoredDAOImpl(this));
        syncService.setActivity(this);
        syncService.execute();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, TutoredActivity.class));
        finish();
    }
}
