package hmf.com.project.hmfinspection.Adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hmf.com.project.hmfinspection.Fragments.InspectionCompleted;
import hmf.com.project.hmfinspection.Fragments.InspectionPending;

/**
 * Created by home on 5/3/2018.
 */

public class InspectionPageAdpter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public InspectionPageAdpter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                InspectionPending tab1 = new InspectionPending();
                return tab1;
            case 1:
                InspectionCompleted tab2 = new InspectionCompleted();
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

