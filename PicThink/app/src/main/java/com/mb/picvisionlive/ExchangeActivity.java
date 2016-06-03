package com.mb.picvisionlive;

import android.content.Context;
import android.widget.ListView;

import com.mb.picvisionlive.adapter.ExchangeListAdapter;
import com.mb.picvisionlive.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExchangeActivity extends BaseActivity {
    Context context=ExchangeActivity.this;
    List<RecordBean> list = new ArrayList<RecordBean>();
    ExchangeListAdapter mExchangeListAdapter;

    @Bind(R.id.exchange_listView)
    ListView mExchangeListView;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_exchange);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("兑换");
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
        mExchangeListAdapter=new ExchangeListAdapter(context,list);
        mExchangeListView.setAdapter(mExchangeListAdapter);
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


}
