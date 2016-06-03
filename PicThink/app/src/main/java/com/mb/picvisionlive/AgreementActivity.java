package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class AgreementActivity extends BaseActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
           initHead("协议");
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
