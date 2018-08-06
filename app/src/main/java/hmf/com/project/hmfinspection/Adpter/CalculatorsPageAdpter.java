package hmf.com.project.hmfinspection.Adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hmf.com.project.hmfinspection.Fragments.RoiFragment;
import hmf.com.project.hmfinspection.Fragments.YeildFragment;


/**
 * Created by home on 5/7/2018.
 */

public class CalculatorsPageAdpter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public CalculatorsPageAdpter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RoiFragment tab1 = new RoiFragment();
                return tab1;
            case 1:
                YeildFragment tab2 = new YeildFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

