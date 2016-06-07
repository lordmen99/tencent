package com.mb.picvisionlive;

import android.content.Context;
import android.widget.ListView;

import com.mb.picvisionlive.adapter.MyOnAirAdapter;
import com.tencent.qcloud.suixinbo.model.LiveInfoJson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyOnairActivity extends BaseActivity {

    Context context = MyOnairActivity.this;
    List<LiveInfoJson> list = new ArrayList<LiveInfoJson>();
    MyOnAirAdapter mMyOnAirAdapter;
    @Bind(R.id.my_onair_listView)
    ListView myOnairListView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_onair);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("我的直播");
        getData();
    }

    private void getData() {
        list.clear();;
        for (int i = 0; i < 10; i++) {
            LiveInfoJson bean = new LiveInfoJson();
            bean.setWatchCount(6000 + i);
            list.add(bean);
        }
        mMyOnAirAdapter = new MyOnAirAdapter(context, list);
        myOnairListView.setAdapter(mMyOnAirAdapter);
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
