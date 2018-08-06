package hmf.com.project.hmfinspection.Adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import hmf.com.project.hmfinspection.Fragments.ConsumerComplete;
import hmf.com.project.hmfinspection.Fragments.ConsumerPending;

/**
 * Created by home on 5/11/2018.
 */

public class ConsumerFormPageAdpter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ConsumerFormPageAdpter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ConsumerPending tab1 = new ConsumerPending();
                return tab1;
            case 1:
                ConsumerComplete tab2 = new ConsumerComplete();
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

