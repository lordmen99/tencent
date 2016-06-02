package com.mb.picvisionlive;

import android.os.Bundle;

import butterknife.ButterKnife;

public class InviteFriendActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("邀请好友");

    }

    @Override
    public void bodymethod() {

    }
}
