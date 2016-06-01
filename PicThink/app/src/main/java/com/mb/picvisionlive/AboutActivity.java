package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {



    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
      initHead("关于我们");
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
