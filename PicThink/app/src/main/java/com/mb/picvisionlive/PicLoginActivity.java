package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class PicLoginActivity extends BaseActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pic_login);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("手机号登录");
    }

    @Override
    public void bodymethod() {

    }
}
