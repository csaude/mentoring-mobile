package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.ItemAdapter;
import mz.org.fgh.mentoring.model.ItemType;
import mz.org.fgh.mentoring.model.MentoringItem;

public class MainActivity extends BaseAuthenticateActivity implements AdapterView.OnItemClickListener {

    private ListView listItems;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_main);
        listItems = (ListView) findViewById(R.id.main_items);
        loadMainItems();

        listItems.setOnItemClickListener(this);
    }

    private void loadMainItems() {

        ItemAdapter itemAdapter =
                new ItemAdapter(this, Arrays.asList(new MentoringItem(getString(R.string.mentoring_process), R.mipmap.ic_process, ItemType.MENTORING_PROCESS),
                        new MentoringItem(getString(R.string.tudoreds), R.mipmap.ic_tutored, ItemType.TUDOREDS),
                        new MentoringItem(getString(R.string.reports), R.mipmap.ic_report, ItemType.REPORTS)));

        listItems.setAdapter(itemAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MentoringItem item = (MentoringItem) parent.getItemAtPosition(position);

        switch (item.getItemType()) {
            case MENTORING_PROCESS:
                Toast.makeText(this, item.getItemTitle() + " Foi clicado", Toast.LENGTH_SHORT).show();
                break;

            case TUDOREDS:
                Toast.makeText(this, item.getItemTitle() + " Foi clicado", Toast.LENGTH_SHORT).show();
                break;

            case REPORTS:
                Toast.makeText(this, item.getItemTitle() + " Foi clicado", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.main_menu_settings:
                startActivity(new Intent(MainActivity.this, ConfigurationActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
