package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.callback.ConfigCallback;
import mz.org.fgh.mentoring.config.model.Location;

public class ConfigurationActivity extends BaseAuthenticateActivity {

    private ListView configItems;
    private ActionMode actionMode;
    private ArrayAdapter<Location> adapter;
    private EditText ipField;
    private List<Location> selectedItems;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_configuration);

        ipField = (EditText) findViewById(R.id.config_server_ip);

        configItems = (ListView) findViewById(R.id.config_items);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, Arrays.asList(Location.HEALTH_FACILITIES, Location.CAREERS, Location.FORMS));
        configItems.setAdapter(adapter);

        configItems.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        configItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (selectedItems == null) {
                    selectedItems = new ArrayList<>();
                }

                CheckedTextView item = (CheckedTextView) view;
                Location value = (Location) parent.getItemAtPosition(position);

                if (item.isChecked()) {
                    selectedItems.add(value);
                } else {
                    selectedItems.remove(value);
                }

                if (actionMode == null) {
                    actionMode = startSupportActionMode(new ConfigCallback(ConfigurationActivity.this));
                }
            }
        });

        ipField.setText(application.getSharedPreferences().getString(getResources().getString(R.string.serve_address), ""));
    }

    public void setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
    }

    public void clearSelection() {
        configItems.clearChoices();
        selectedItems = new ArrayList<>();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.config_menu_ok:
                if (ipField.getText().length() == 0) {
                    ipField.setError("Endereço invalido!");
                    return true;
                }

                application.getSharedPreferences().edit()
                        .putString(getResources().getString(R.string.serve_address), ipField.getText().toString())
                        .apply();

                application.updateRetrofit();

                Toast.makeText(this, "Endereçao do servidor actulizado!", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    public List<Location> getSelectedItems() {
        return this.selectedItems;
    }
}
