package com.mb.picthinklive;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mb.picthinklive.banner.views.CBLoopViewPager;
import com.mb.picthinklive.banner.views.CBViewHolderCreator;
import com.mb.picthinklive.banner.views.ConvenientBanner;
import com.mb.picthinklive.banner.views.NetworkImageHolderView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {
    //轮播图
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    //轮播图数量
    @Bind(R.id.tv_banner_count)
    TextView tvBannerCount;
    //查看所有商品
    @Bind(R.id.rl_look)
    RelativeLayout rlLook;

    private List<String> networkImages;
    private String[] images = {
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    @Override
    public void setContentView() {

        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);

        //加载轮播图
        CBLoopViewPager.setTextView(tvBannerCount, images.length);
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages);

        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//      convenientBanner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})

    }

    private int hehe;

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);

    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }


    @Override
    public void findViewByid() {
        initHead("商品详情");
        rlLook.setOnClickListener(this);
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_look:
                finish();
                break;
        }
    }
}
