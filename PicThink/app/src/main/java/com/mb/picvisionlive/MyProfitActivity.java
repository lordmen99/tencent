package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyProfitActivity extends BaseActivity {
    Context context = MyProfitActivity.this;

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



    @OnClick({R.id.commom_right_txt, R.id.my_profit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commom_right_txt:{
                Intent intent = new Intent(context, ProfitRecordActivity.class);
                startActivity(intent);
                break;}
            case R.id.my_profit_btn:{
                Intent intent = new Intent(context, ExchangeActivity.class);
                startActivity(intent);
                break;}
        }
    }


}
