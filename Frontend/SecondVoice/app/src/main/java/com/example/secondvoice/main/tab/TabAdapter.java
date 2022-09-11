package com.example.secondvoice.main.tab;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public final class TabAdapter extends FragmentPagerAdapter {

    private final List<Tab> tabs = new ArrayList<>();
    private final List<String> tabTitles = new ArrayList<>();

    public TabAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addTab(Tab tab,String username) {
        tabs.add(tab);
        tabTitles.add(tab.getTitle());
        tab.setName(username);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
