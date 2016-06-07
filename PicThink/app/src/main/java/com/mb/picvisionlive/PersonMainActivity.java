package com.mb.picvisionlive;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.picvisionlive.adapter.PlayBackAdapter;
import com.mb.picvisionlive.weight.ListViewForScrollView;
import com.tencent.qcloud.suixinbo.model.LiveInfoJson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonMainActivity extends BaseActivity {
    Context context = PersonMainActivity.this;
    List<LiveInfoJson> list = new ArrayList<LiveInfoJson>();
    @Bind(R.id.person_main_index_txt)
    TextView mIndexTxt;
    @Bind(R.id.person_main_onair_txt)
    TextView mOnairTxt;
    @Bind(R.id.person_main_part2_line)
    LinearLayout mPart2Line;
    @Bind(R.id.person_main_playback_listView)
    ListViewForScrollView mListView;
    @Bind(R.id.person_main_part3_line)
    LinearLayout mPart3Line;


    PlayBackAdapter mPlayBackAdapter;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_person_main);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        getListData();
    }

    private void getListData() {
        list.clear();
        for (int i = 0; i < 5; i++) {
            LiveInfoJson bean=new LiveInfoJson();
            bean.setTitle("卡不爱看"+i);
            list.add(bean);
        }
        mPlayBackAdapter=new PlayBackAdapter(context,list);
        mListView.setAdapter(mPlayBackAdapter);

    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


    @OnClick({R.id.person_main_index_txt, R.id.person_main_onair_txt,R.id.common_back_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_main_index_txt:{
                //
                mOnairTxt.setTextColor(Color.parseColor("#484848"));
                mIndexTxt.setTextColor(Color.parseColor("#F8D150"));
                mPart3Line.setVisibility(View.GONE);
                mPart2Line.setVisibility(View.VISIBLE);
                break;}
            case R.id.person_main_onair_txt:{
                mIndexTxt.setTextColor(Color.parseColor("#484848"));
                mOnairTxt.setTextColor(Color.parseColor("#F8D150"));
                mPart2Line.setVisibility(View.GONE);
                mPart3Line.setVisibility(View.VISIBLE);
                break;}
            case R.id.common_back_img:{
               finish();
                break;}
        }
    }
}
