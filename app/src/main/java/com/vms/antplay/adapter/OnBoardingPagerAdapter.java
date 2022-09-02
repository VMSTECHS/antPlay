package com.vms.antplay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vms.antplay.fragments.OnBoardingFive;
import com.vms.antplay.fragments.OnBoardingFour;
import com.vms.antplay.fragments.OnBoardingOne;
import com.vms.antplay.fragments.OnBoardingSix;
import com.vms.antplay.fragments.OnBoardingThree;
import com.vms.antplay.fragments.OnBoardingTwo;

public class OnBoardingPagerAdapter extends FragmentStatePagerAdapter {

    int PAGE_COUNT = 6;


    public OnBoardingPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OnBoardingOne();
            case 1:
                return new OnBoardingTwo();
            case 2:
                return new OnBoardingThree();
            case 3:
                return new OnBoardingFour();
            case 4:
                return new OnBoardingFive();
            case 5:
                return new OnBoardingSix();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
