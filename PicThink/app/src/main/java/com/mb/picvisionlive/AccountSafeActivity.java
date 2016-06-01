package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class AccountSafeActivity extends BaseActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_account_safe);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
       initHead("账号与安全");
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
