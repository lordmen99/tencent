package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class InviteFriendActivity extends BaseActivity {



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
