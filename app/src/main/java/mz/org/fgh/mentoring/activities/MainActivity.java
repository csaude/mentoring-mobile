package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Arrays;

import javax.inject.Inject;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.ItemAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.model.ItemType;
import mz.org.fgh.mentoring.model.MentoringItem;
import mz.org.fgh.mentoring.service.LoadMetadataService;

public class MainActivity extends BaseAuthenticateActivity implements AdapterView.OnItemClickListener {

    private ListView listItems;

    @Inject
    LoadMetadataService loadMetadataService;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_main);

        MentoringComponent mentoringComponent = application.getMentoringComponent();
        mentoringComponent.inject(this);

        listItems = (ListView) findViewById(R.id.main_items);
        listItems.setOnItemClickListener(this);

        loadMainItems();

        loadMetadataService.load(this, application.getAuth().getUser());
    }

    private void loadMainItems() {

        ItemAdapter itemAdapter =
                new ItemAdapter(this, Arrays.asList(new MentoringItem(getString(R.string.mentoring_process), R.mipmap.ic_process, ItemType.MENTORING_PROCESS),
                        new MentoringItem(getString(R.string.tudoreds), R.mipmap.ic_tutored, ItemType.TUDOREDS),
                        new MentoringItem(getString(R.string.indicators), R.mipmap.ic_indicators, ItemType.INDICATORS),
                        new MentoringItem(getString(R.string.reports), R.mipmap.ic_report, ItemType.REPORTS)));

        listItems.setAdapter(itemAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MentoringItem item = (MentoringItem) parent.getItemAtPosition(position);

        switch (item.getItemType()) {

            case MENTORING_PROCESS:
                startActivity(new Intent(MainActivity.this, ListMentorshipActivity.class));
                break;

            case TUDOREDS:
                startActivity(new Intent(MainActivity.this, ListTutoredActivity.class));
                break;

            case INDICATORS:
                startActivity(new Intent(MainActivity.this, ListIndicatorsActivity.class));
                break;

            case REPORTS:
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

            case R.id.main_menu_logout:
                application.logout();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.main_menu_tutor_details:
                startActivity(new Intent(MainActivity.this, TutorUpdateActivity.class));
                break;

            case R.id.main_menu_change_password:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
