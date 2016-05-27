package com.mb.picthinklive.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class MyFriendFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;

    public MyFriendFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> list) {
        super(fragmentManager);
        this.list = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}