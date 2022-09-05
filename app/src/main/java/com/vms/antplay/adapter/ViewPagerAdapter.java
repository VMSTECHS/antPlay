package com.vms.antplay.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.vms.antplay.fragments.GamePadFragment;
import com.vms.antplay.fragments.PCSettingFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new PCSettingFragment();
        } else if (position == 1) {
            fragment = new GamePadFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "PC Settings";
        } else if (position == 1) {
            title = "Gamepad";
        }
        return title;
    }
}
