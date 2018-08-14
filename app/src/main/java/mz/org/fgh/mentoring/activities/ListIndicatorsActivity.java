package mz.org.fgh.mentoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.IndicatorAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.process.dao.IndicatorDAO;
import mz.org.fgh.mentoring.process.model.Indicator;
import mz.org.fgh.mentoring.service.IndicatorService;

public class ListIndicatorsActivity extends BaseAuthenticateActivity implements View.OnClickListener {

    @BindView(R.id.new_indicator)
    Button newIndicatorBtn;

    @BindView(R.id.indicator_list)
    ListView indicatorListView;

    @Inject
    IndicatorDAO indicatorDAO;

    @Inject
    IndicatorService indicatorService;

    @Inject
    EventBus eventBus;

    @Override
    protected void onMentoringCreate(Bundle bundle) {
        setContentView(R.layout.activity_list_indicators);
        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);
        eventBus.register(this);

        setIndicators();
        newIndicatorBtn.setOnClickListener(this);
    }

    public void setIndicators() {
        List<Indicator> indicators = indicatorDAO.findAll();
        IndicatorAdapter adapter = new IndicatorAdapter(this, indicators);
        indicatorListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, IndicatorActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.indicator_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.indicator_menu_sync:
                indicatorService.setActivity(this);
                indicatorService.syncIndicators(application.getAuth().getUser());
                break;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    @Subscribe
    public void onSync(ProcessEvent processEvent) {
        setIndicators();
    }
}
