package mz.org.fgh.mentoring.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;

public class MentoringActivity extends FragmentActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentoring);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        SwipeAdapter adapter = new SwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
}
