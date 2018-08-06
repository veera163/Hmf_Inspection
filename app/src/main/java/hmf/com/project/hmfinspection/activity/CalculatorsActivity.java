package hmf.anyasoft.es.Hmf_inspection.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import hmf.anyasoft.es.Hmf_inspection.Adpter.CalculatorsPageAdpter;
import hmf.anyasoft.es.Hmf_inspection.R;


/**
 * Created by home on 5/3/2018.
 */

public class CalculatorsActivity extends AppCompatActivity {

    ViewPager viewPager;
    CalculatorsPageAdpter calculatorsPageAdpter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("ROI"));
        tabLayout.addTab(tabLayout.newTab().setText("YEILD"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
        calculatorsPageAdpter = new CalculatorsPageAdpter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(calculatorsPageAdpter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
}
