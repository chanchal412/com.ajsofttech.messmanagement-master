package com.project.messmanagement.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.messmanagement.fragments.FragmentManager2;
import com.project.messmanagement.fragments.FragmentMyOrders;

public class PagerAdapterManager extends FragmentPagerAdapter {
    public PagerAdapterManager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new FragmentManager2();

        }
        if (position == 1) {
            fragment = new FragmentMyOrders();

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
            value = "My mess";

        }
        if (position == 1) {
            value = "My Customers";

        }
        return value;
    }
}