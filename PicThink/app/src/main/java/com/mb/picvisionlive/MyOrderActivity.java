package com.mb.picvisionlive;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.mb.picvisionlive.fragment.BuyFragment;
import com.mb.picvisionlive.fragment.SellFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {


    @Bind(R.id.tv_sell)
    TextView tvSell;
    @Bind(R.id.tv_buy)
    TextView tvBuy;
    @Bind(R.id.v_line)
    View vLine;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    private ViewTreeObserver vto;
    private int bmWidth;
    private int gapWidth;
    int beforeItem = 0;
    private List<Fragment> list = new ArrayList<Fragment>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initViewPage();
    }

    @Override
    public void findViewByid() {
        vto = vLine.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(mGlobalLayoutListener);
        initHead("我的订单");
    }

    @Override
    public void bodymethod() {

    }
    private void initViewPage( ) {

        list.add(new SellFragment());//出售记录
        list.add(new BuyFragment());//购买记录

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        MoveImage();
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

            try {
                width = vLine.getWidth();
            } catch (Exception e) {
            }

            if (width != -1 && bmWidth != width) {//只有当尺寸真正有了数值，即已经确定了，更新UI才有意义
                bmWidth = width;
                setImageStartMove();
            }
        }
    };
    /**
     * 让图片移动到起始位置
     */

    private void setImageStartMove() {

       /*获取屏幕宽度*/
        int screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
       /*计算间隙宽度*/
//        int txtWidth = bmWidth * 3;
//        gapWidth = (screenWidth - txtWidth) / 2;
//        int txtWidth = bmWidth * 2;
//        gapWidth = (screenWidth - txtWidth) / 2;
        gapWidth = bmWidth/2;
    }

    private void MoveImage( ) {

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
                Log.i("Home",bmWidth+"字体宽度");

                Animation animation = null;
                switch (position) {
                    case 0: {
                        if (beforeItem == 1) {
                            animation = new TranslateAnimation(one, 0, 0, 0);
                        } else {
                            animation = new TranslateAnimation(one * 2, 0, 0, 0);
                        }
                        animation.setDuration(200);//设置动画的持续时间
                        animation.setFillAfter(true);//让动画停止在结束位置
                        vLine.startAnimation(animation);
                        beforeItem = position;

                        break;
                    }
                    case 1: {

                        if (beforeItem == 0) {
                            animation = new TranslateAnimation(0, one, 0, 0);
                        } else {
                            animation = new TranslateAnimation(one * 2, one, 0, 0);
                        }
                        animation.setDuration(200);//设置动画的持续时间
                        animation.setFillAfter(true);//让动画停止在结束位置
                        vLine.startAnimation(animation);
                        beforeItem = position;
                        break;
                    }

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
    @OnClick({R.id.tv_sell, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sell:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_buy:
                viewPager.setCurrentItem(1);
                break;

        }
    }

}
