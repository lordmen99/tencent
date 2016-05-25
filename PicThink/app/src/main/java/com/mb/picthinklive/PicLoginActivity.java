package com.mb.picthinklive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
