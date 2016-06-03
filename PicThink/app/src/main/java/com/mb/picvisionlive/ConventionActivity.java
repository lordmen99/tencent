package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class ConventionActivity extends BaseActivity {



    @Override
    public void setContentView() {
        setContentView(R.layout.activity_convention);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {

        initHead(getIntent().getStringExtra("title"));
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
