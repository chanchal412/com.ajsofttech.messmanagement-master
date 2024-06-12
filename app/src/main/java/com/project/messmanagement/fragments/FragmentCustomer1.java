package com.project.messmanagement.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.messmanagement.R;
import com.project.messmanagement.adapters.PagerAdapterCustomer;
import com.google.android.material.tabs.TabLayout;


public class FragmentCustomer1 extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v=inflater.inflate(R.layout.fragment_customer1, container, false);
        viewPager=v.findViewById(R.id.viewPager);
        tabLayout=v.findViewById(R.id.tabLayout);
        viewPager.setAdapter(new PagerAdapterCustomer(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }
}