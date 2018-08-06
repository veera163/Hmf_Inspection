package hmf.anyasoft.es.Hmf_inspection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import hmf.anyasoft.es.Hmf_inspection.Adpter.InspectionPageAdpter;
import hmf.anyasoft.es.Hmf_inspection.R;

/**
 * Created by home on 5/2/2018.
 */

public class InspectionActivity extends AppCompatActivity {

    ViewPager viewPager;
    InspectionPageAdpter inspectionPageAdpter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("PENDING"));
        tabLayout.addTab(tabLayout.newTab().setText("COMPLETED"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
          viewPager = (ViewPager) findViewById(R.id.pager);
        inspectionPageAdpter = new InspectionPageAdpter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(inspectionPageAdpter);

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
        Intent i= new Intent(InspectionActivity.this,LandingActivity.class);
        startActivity(i);
    }
}
