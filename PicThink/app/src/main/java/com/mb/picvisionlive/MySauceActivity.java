package com.mb.picvisionlive;

import android.content.Context;
import android.widget.ListView;

import com.mb.picvisionlive.adapter.SauceListAdapter;
import com.mb.picvisionlive.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MySauceActivity extends BaseActivity {
    Context context=MySauceActivity.this;
    List<RecordBean> list = new ArrayList<RecordBean>();
    SauceListAdapter mSauceListAdapter;

    @Bind(R.id.my_sauce_listView)
    ListView mSauceListView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_sauce);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("我的酱");
    }

    @Override
    public void bodymethod() {
        getDatas();
    }
    private void getDatas() {
        for (int i = 0; i < 18; i++) {
            RecordBean bean = new RecordBean();
            bean.setNumber(200+i);

            list.add(bean);
        }
        mSauceListAdapter=new SauceListAdapter(context,list);
        mSauceListView.setAdapter(mSauceListAdapter);
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


}
