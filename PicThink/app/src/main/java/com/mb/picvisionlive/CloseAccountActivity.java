package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class CloseAccountActivity extends BaseActivity {

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_close_account);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
initHead("封号系统");
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
