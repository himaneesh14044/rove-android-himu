package com.gursimransinghhanspal.rove.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Himaneesh on 10-03-2018.
 */
public class SectionPagerAdapter extends FragmentPagerAdapter
{
    int tabcount;
    public SectionPagerAdapter(FragmentManager m,int tabcount)
    {
        super(m);
        this.tabcount = tabcount;
    }
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
