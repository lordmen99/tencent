package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class HelpDetailActivity extends BaseActivity {



    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("帮助和反馈");
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
