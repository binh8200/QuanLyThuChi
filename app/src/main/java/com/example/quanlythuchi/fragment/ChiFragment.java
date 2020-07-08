package com.example.quanlythuchi.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.SectionsPagerAdapter;

public class ChiFragment extends Fragment {

    ViewPager viewPager2;
    TabLayout tabLayout;

    public ChiFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //-----viewpager-----
        viewPager2 = view.findViewById(R.id.viewpager2);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        sectionsPagerAdapter.addFragment(new KhoanchiFragment());
        sectionsPagerAdapter.addFragment(new LoaichiFragment());
        viewPager2.setAdapter(sectionsPagerAdapter);

        //-----tab layout-----
        tabLayout = view.findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(viewPager2);
        tabLayout.getTabAt(0).setText("Khoản chi");
        tabLayout.getTabAt(1).setText("Loại chi");
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public interface OnFragmentInteractionListener {
    }
}
