package hmf.com.project.hmfinspection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import hmf.com.project.hmfinspection.Adpter.ConsumerFormPageAdpter;
import hmf.com.project.hmfinspection.R;

/**
 * Created by home on 5/11/2018.
 */

public class ConsumerInspectionActivity extends AppCompatActivity {

    ViewPager viewPager;
    ConsumerFormPageAdpter consumerFormPageAdpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("PENDING"));
        tabLayout.addTab(tabLayout.newTab().setText("COMPLETED"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = (ViewPager) findViewById(R.id.pager);
        consumerFormPageAdpter = new ConsumerFormPageAdpter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(consumerFormPageAdpter);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i= new Intent(ConsumerInspectionActivity.this,LandingActivity.class);
        startActivity(i);
    }
}
