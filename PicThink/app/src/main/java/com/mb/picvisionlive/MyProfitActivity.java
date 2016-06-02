package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfitActivity extends BaseActivity {
Context context=MyProfitActivity.this;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_profit);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("我的收益", "领取记录");
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }



    @OnClick(R.id.commom_right_txt)
    public void onClick() {
        Intent intent = new Intent(context, ProfitRecordActivity.class);
        startActivity(intent);
    }
}
