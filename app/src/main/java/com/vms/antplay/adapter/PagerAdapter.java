package com.vms.antplay.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vms.antplay.fragments.ComputerFragment;
import com.vms.antplay.fragments.HomeFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                ComputerFragment tab2 = new ComputerFragment();
                return tab2;
//            case 2:
//                ThirdFragment tab3 = new ThirdFragment();
//                return tab3;
            default:
              //  HomeFragment tab4 = new HomeFragment();
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}