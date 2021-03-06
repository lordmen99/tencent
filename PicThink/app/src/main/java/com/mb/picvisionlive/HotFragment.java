package com.mb.picvisionlive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mb.picvisionlive.adapter.MyPagerAdapter;
import com.mb.picvisionlive.banner.views.CBViewHolderCreator;
import com.mb.picvisionlive.banner.views.ConvenientBanner;
import com.mb.picvisionlive.banner.views.NetworkImageHolderView;
import com.mb.picvisionlive.weight.MyScrollView;
import com.tencent.qcloud.suixinbo.adapters.LiveShowAdapter;
import com.tencent.qcloud.suixinbo.model.CurLiveInfo;
import com.tencent.qcloud.suixinbo.model.LiveInfoJson;
import com.tencent.qcloud.suixinbo.model.MySelfInfo;
import com.tencent.qcloud.suixinbo.presenters.LiveListViewHelper;
import com.tencent.qcloud.suixinbo.presenters.viewinface.LiveListView;
import com.tencent.qcloud.suixinbo.utils.Constants;
import com.tencent.qcloud.suixinbo.views.LiveActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/1/13.
 */
public class HotFragment extends Fragment implements LiveListView, SwipeRefreshLayout.OnRefreshListener {
    protected Context context;
    @Bind(R.id.layout_point_parent)
    LinearLayout layoutPointParent;
    @Bind(R.id.fragment_hot_live_list)
    ListView mLiveList;
    @Bind(R.id.swipe_refresh_layout_list)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<View> mViews = new ArrayList<View>();
    ;
//
//    @Bind(R.id.frgment_hot_viewPager)
//    ViewPager frgmentHotViewPager;
    @Bind(R.id.fragment_hot_scrollview)
    MyScrollView fragmentHotScrollview;

    private MyPagerAdapter mAdapter;
    private HashMap<Integer, ImageView> mImageMap = new HashMap<Integer, ImageView>();

    private ArrayList<LiveInfoJson> liveList = new ArrayList<LiveInfoJson>();
    private LiveShowAdapter adapter;
    private LiveListViewHelper mLiveListViewHelper;

    //轮播图
    @Bind(R.id.convenientBanner)
    ConvenientBanner convenientBanner;

    private List<String> networkImages;
    private String[] images = {
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };
    /**
     * Fragment需要第一次绘制界面时，系统调用此方法。为了创建一个UI，必须返回一个View，这个View是从Fragment的Layout文件中得到的。如果你的Fragment不想提供UI的，就返回null
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();


        View view = inflater.inflate(R.layout.fragment_hot, null);
        ButterKnife.bind(this, view);

        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
      convenientBanner.setPageIndicator(new int[]{R.mipmap.frament_hot_tiao_b, R.mipmap.frament_hot_tiao_a});

        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages);

        initListView();
        return view;
    }

    private void initListView() {
        mLiveListViewHelper = new LiveListViewHelper(context, this);


        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        adapter = new LiveShowAdapter(getActivity(), R.layout.item_liveshow, liveList);
        mLiveList.setAdapter(adapter);

        mLiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LiveInfoJson item = liveList.get(i);
                //如果是自己
                if (item.getHost().getUid().equals(MySelfInfo.getInstance().getId())) {
                    Toast.makeText(getActivity(), "this room don't exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), LiveActivity.class);
                intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                MySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                CurLiveInfo.setHostID(item.getHost().getUid());
                CurLiveInfo.setHostName(item.getHost().getUsername());
                CurLiveInfo.setHostAvator(item.getHost().getAvatar());
                CurLiveInfo.setRoomNum(item.getAvRoomId());
                CurLiveInfo.setMembers(item.getWatchCount() + 1); // 添加自己
                CurLiveInfo.setAdmires(item.getAdmireCount());
                CurLiveInfo.setAddress(item.getLbs().getAddress());
                startActivity(intent);
                // SxbLog.i(TAG, "PerformanceTest  join Live     " + SxbLog.getTime());
            }
        });
    }




    /**
     * 当创建Fragment的时候，系统调用这个方法。在你的方法实现中，你要初始化你必须的组件。
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 当系统调用此方法时，第一表明用户已经离开了此Fragment。通常要在这个方法内提交发生的变化。
     */
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }


    /**
     * 当fragment被加入到activity时调用（在这个方法中可以获得所在的activity）
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
    }

    /**
     * 当fragment被从activity中删掉时被调用。
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 当fragment的layout被销毁时被调用。
     */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        mLiveListViewHelper.getPageData();
        super.onStart();
    }

    @Override
    public void showFirstPage(ArrayList<LiveInfoJson> result) {
         mSwipeRefreshLayout.setRefreshing(false);
        liveList.clear();
        if (null != result) {
            for (LiveInfoJson item : result) {
                liveList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        mLiveListViewHelper.getPageData();
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);

    }


}
