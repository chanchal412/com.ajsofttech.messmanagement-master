package com.project.messmanagement.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.messmanagement.fragments.FragmentMyJionedmess;
import com.project.messmanagement.fragments.FragmentCutomer2;

public class PagerAdapterCustomer extends FragmentPagerAdapter {
    public PagerAdapterCustomer(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {

            fragment = new FragmentCutomer2();

        }
        if (position == 1) {
            fragment = new FragmentMyJionedmess();

        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String value = null;


        if (position == 0) {
            value = "Mess list";

        }
        if (position == 1) {
            value = "Joined mess";

        }
        return value;
    }
}