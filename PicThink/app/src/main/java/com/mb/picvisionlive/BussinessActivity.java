package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class BussinessActivity extends BaseActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bussiness);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("电商入驻");
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
