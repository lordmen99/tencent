package com.mb.picthinklive.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mb.picthinklive.R;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class ShowFriendFragmentB extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_show_friends_b, container, false);

        return rootView;
    }
}
