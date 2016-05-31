package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.mb.picvisionlive.adapter.HeadAdapter;
import com.mb.picvisionlive.bean.PersonBean;
import com.mb.picvisionlive.weight.ListViewForScrollView;
import com.mb.picvisionlive.weight.MyGridView;
import com.tencent.qcloud.suixinbo.adapters.LiveShowAdapter;
import com.tencent.qcloud.suixinbo.model.CurLiveInfo;
import com.tencent.qcloud.suixinbo.model.LiveInfoJson;
import com.tencent.qcloud.suixinbo.model.MySelfInfo;
import com.tencent.qcloud.suixinbo.presenters.LiveListViewHelper;
import com.tencent.qcloud.suixinbo.presenters.viewinface.LiveListView;
import com.tencent.qcloud.suixinbo.utils.Constants;
import com.tencent.qcloud.suixinbo.views.LiveActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/1/13.
 */
public class LookFragment extends Fragment implements LiveListView, SwipeRefreshLayout.OnRefreshListener{
    Context context;
    @Bind(R.id.fragment_look_live_list)
    ListViewForScrollView mLookLiveList;
    @Bind(R.id.fragment_look_daren_gridview)
    MyGridView mLookDarenGridview;
    @Bind(R.id.fragment_look_empty_line)
    LinearLayout mLookEmptyLine;
    @Bind(R.id.swipe_refresh_layout_list)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<LiveInfoJson> liveList = new ArrayList<LiveInfoJson>();
    private LiveShowAdapter adapter;
    private LiveListViewHelper mLiveListViewHelper;


    HeadAdapter mheadDarenAdapter;

    List<PersonBean> darenList = new ArrayList<PersonBean>();

    int a = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();

        View view = inflater.inflate(R.layout.fragment_look, null);
        ButterKnife.bind(this, view);

         a = 0;

        if (a == 1) {
            mLookEmptyLine.setVisibility(View.GONE);
            initLiveListView();
        }else{
            mLookLiveList.setVisibility(View.GONE);
            mLookEmptyLine.setVisibility(View.VISIBLE);
            initDarenGridView();
        }

        if (a==1){
            mLiveListViewHelper.getPageData();
        }

        return view;
    }

    private void initLiveListView() {
        mLiveListViewHelper = new LiveListViewHelper(context, this);

        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        adapter = new LiveShowAdapter(getActivity(), R.layout.item_liveshow, liveList);
        mLookLiveList.setAdapter(adapter);

        mLookLiveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void initDarenGridView() {
        for (int i = 0; i < 18; i++) {
            PersonBean person = new PersonBean();
            person.setImg_id(i);

            darenList.add(person);



        }

        mheadDarenAdapter = new HeadAdapter(context, darenList);

        mLookDarenGridview.setAdapter(mheadDarenAdapter);

    }
    @Override
    public void onStart() {
//        if (a==1){
//            mLiveListViewHelper.getPageData();
//        }

        super.onStart();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showFirstPage(ArrayList<LiveInfoJson> result) {
        if (a==1){
            mSwipeRefreshLayout.setRefreshing(false);
            liveList.clear();
            if (null != result) {
                for (LiveInfoJson item : result) {
                    liveList.add(item);
                }
            }
            adapter.notifyDataSetChanged();
    }

    }

    @Override
    public void onRefresh() {
        if (a==1){
            mLiveListViewHelper.getPageData();
        }

    }
}
