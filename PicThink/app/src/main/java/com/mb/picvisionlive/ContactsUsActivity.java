package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class ContactsUsActivity extends BaseActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_contacts_us);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("联系我们");
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
