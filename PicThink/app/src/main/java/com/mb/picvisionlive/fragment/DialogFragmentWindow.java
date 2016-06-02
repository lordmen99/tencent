package com.mb.picvisionlive.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.adapter.MyFriendFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:好友、未关注
 */
public class DialogFragmentWindow extends DialogFragment implements View.OnClickListener {

    private ArrayList<Fragment> fList;
    private LinearLayout ll_friend;
    private ViewPager vPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pop_friends_window, container);

        vPager = (ViewPager) view.findViewById(R.id.vPager);
        ll_friend = (LinearLayout) view.findViewById(R.id.ll_friend);
        TextView tv_good_friend = (TextView) view.findViewById(R.id.tv_good_friend);
        TextView tv_no_concerned = (TextView) view.findViewById(R.id.tv_no_concerned);
        tv_good_friend.setOnClickListener(this);
        tv_no_concerned.setOnClickListener(this);

        ll_friend.getChildAt(0).setSelected(true);

        fList = new ArrayList<Fragment>();
        fList.add(new ShowFriendFragmentA());
        fList.add(new ShowFriendFragmentB());

        MyFriendFragmentPagerAdapter ama = new MyFriendFragmentPagerAdapter(getChildFragmentManager(), fList);
        vPager.setAdapter(ama);
        vPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        Window window =   getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;

        window.setAttributes(wlp);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_good_friend://好友
                vPager.setCurrentItem(0);
                break;
            case R.id.tv_no_concerned://未关注
                vPager.setCurrentItem(1);
                break;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < fList.size(); i++) {
                ll_friend.getChildAt(i).setSelected(i == arg0);
            }
        }
    }


@Override
public void onStart()
{
    super.onStart();
    DisplayMetrics dm = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
    getDialog().getWindow().setLayout( dm.widthPixels, getDialog().getWindow().getAttributes().height );
}
}