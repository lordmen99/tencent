package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class PushMessageActivity extends BaseActivity {

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_push_message);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
initHead("推送管理");
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
