package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
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

    private DrawerLayout drawerLayout;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_main);

        MentoringComponent mentoringComponent = application.getMentoringComponent();
        mentoringComponent.inject(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(getListener());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        setUserName(navigationView);

        listItems = findViewById(R.id.main_items);
        listItems.setOnItemClickListener(this);

        loadMainItems();

        loadMetadataService.load(this, application.getAuth().getUser());
    }

    private void setUserName(NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);

        TextView userTextView = headerView.findViewById(R.id.header_user);
        userTextView.setText(application.getAuth().getUser().getFullName().toLowerCase());

        TextView emailTextView = headerView.findViewById(R.id.header_user_email);
        emailTextView.setText(application.getAuth().getUser().getEmail());
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


    private NavigationView.OnNavigationItemSelectedListener getListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {

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
                        startActivity(new Intent(MainActivity.this, ChangePasswordActivity.class));
                        break;
                }

                drawerLayout.closeDrawers();
                return Boolean.TRUE;
            }
        };
    }
}
