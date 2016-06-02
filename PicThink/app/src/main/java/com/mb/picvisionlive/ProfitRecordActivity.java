package com.mb.picvisionlive;

import android.content.Context;
import android.widget.ListView;

import com.mb.picvisionlive.adapter.RecordListAdapter;
import com.mb.picvisionlive.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfitRecordActivity extends BaseActivity {
    Context context=ProfitRecordActivity.this;
    List<RecordBean> list = new ArrayList<RecordBean>();
    RecordListAdapter mRecordListAdapter;

    @Bind(R.id.profit_record_listView)
    ListView mRecordListView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_profit_record);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("领取记录");
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
        mRecordListAdapter=new RecordListAdapter(context,list);
        mRecordListView.setAdapter(mRecordListAdapter);
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


}
