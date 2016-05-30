package com.mb.picvisionlive;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.tencent.qcloud.suixinbo.views.FragmentLiveList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MainFragment extends Fragment{
    @Bind(R.id.fragment_main_line)
    View fragmentMainLine;
    @Bind(R.id.fragment_main_look_txt)
    TextView fragmentMainLookTxt;
    @Bind(R.id.fragment_main_hot_txt)
    TextView fragmentMainHotTxt;
    @Bind(R.id.fragment_main_new_txt)
    TextView fragmentMainNewTxt;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private FragmentTransaction ft;
    private List<Fragment> list = new ArrayList<Fragment>();
    private int gapWidth;
    private int bmWidth;
    int beforeItem = 0;
    ViewPager viewPager;

    private ViewTreeObserver vto ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, view);

        vto = (ViewTreeObserver) fragmentMainLine .getViewTreeObserver();


        vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        initViewPage(view);
        return view;
    }



    private void initViewPage(View view) {

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        list.add(new LookFragment());//HotFragment()
        list.add(new HotFragment());
        list.add(new MostNewFragment());
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager()));
        MoveImage(view);
    }

    private void MoveImage(View view) {

        //final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int one = gapWidth + bmWidth;
                int two = gapWidth + bmWidth * 2;
                int three = gapWidth + bmWidth * 3;

                Log.e("==case 0==gapWidth===",gapWidth+"");
                Log.e("==case 0==bmWidth===",bmWidth+"");
                Log.e("==case 0==one===",one+"");

//                private int bmpw = 0; // 游标宽度
//                private int offset = 0;// // 动画图片偏移量
//                int one = bmWidth * 2 + bmWidth;// 页卡1 -> 页卡2 偏移量
//                int two = one * 2;// 页卡1 -> 页卡3 偏移量
                //int one = offset * 2 + bmpw;// 页卡1 -> 页卡2 偏移量
                //int two = one * 2;// 页卡1 -> 页卡3 偏移量

                Animation animation = null;
                switch (position) {
                    case 0: {
                        Log.e("==case 0==qian===",beforeItem+"");
                        if (beforeItem == 1) {
                            animation = new TranslateAnimation(bmWidth, 0, 0, 0);
                        } else {
                            animation = new TranslateAnimation(bmWidth*2, 0, 0, 0);
                        }
                        animation.setDuration(200);//设置动画的持续时间
                        animation.setFillAfter(true);//让动画停止在结束位置
                        fragmentMainLine.startAnimation(animation);
                        beforeItem = position;

                        Log.e("==case 0==beforeItem===",beforeItem+"");
                        break;
                    }
                    case 1: {
                        Log.e("==case 1==qian===",beforeItem+"");
                        if (beforeItem == 0) {
                            animation = new TranslateAnimation(0, bmWidth, 0, 0);
                        } else {
                            animation = new TranslateAnimation(bmWidth*2, bmWidth, 0, 0);
                        }
                        animation.setDuration(200);//设置动画的持续时间
                        animation.setFillAfter(true);//让动画停止在结束位置
                        fragmentMainLine.startAnimation(animation);
                        beforeItem = position;
                        Log.e("==case 1==beforeItem===",beforeItem+"");
                        break;
                    }
                    case 2: {
                        Log.e("==case 2==qian===",beforeItem+"");
                        if (beforeItem == 0) {
                            animation = new TranslateAnimation(0, bmWidth*2, 0, 0);
                        } else {
                            animation = new TranslateAnimation(bmWidth, bmWidth*2, 0, 0);
                        }
                        animation.setDuration(200);//设置动画的持续时间
                        animation.setFillAfter(true);//让动画停止在结束位置
                        fragmentMainLine.startAnimation(animation);
                        beforeItem = position;
                        Log.e("==case 2==beforeItem===",beforeItem+"");
                        break;
                    }
                }
               /* animation.setDuration(200);//设置动画的持续时间
                animation.setFillAfter(true);//让动画停止在结束位置
                imageView.startAnimation(animation);
                beforeItem=position;*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    /**
     * 让图片移动到起始位置
     */

    private void setImageStartMove() {

       /*获取屏幕宽度*/
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
       /*计算间隙宽度*/
        int txtWidth=bmWidth*3;
        gapWidth = (screenWidth  - txtWidth) / 2;

//        Log.e("==set==gapWidth===",gapWidth+"");
//        Log.e("==set==bmWidth===",bmWidth+"");
//        Log.e("=set=screenWidth===",screenWidth+"");



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.fragment_main_look_txt, R.id.fragment_main_hot_txt, R.id.fragment_main_new_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_main_look_txt:
                viewPager.setCurrentItem(0);
                break;
            case R.id.fragment_main_hot_txt:
                viewPager.setCurrentItem(1);
                break;
            case R.id.fragment_main_new_txt:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        // 当某子项被摧毁时
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        // 判断该view是否来自对象
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return super.isViewFromObject(view, object);
        }
    }


    private final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            int width = -1;
           // int height = -1;

            try {
                width = fragmentMainLine.getWidth();
                //height = getActivity().getWindow().getDecorView().getHeight();
            } catch (Exception e) {
                // called too early. so, just skip.
            }

            if (width != -1 && bmWidth != width) {//只有当尺寸真正有了数值，即已经确定了，更新UI才有意义
                bmWidth = width;
                setImageStartMove();
            }  } };
}
