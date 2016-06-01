package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class ADDAddressActivity extends BaseActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("地址添加");
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
