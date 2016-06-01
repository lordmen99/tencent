package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class GradeActivity extends BaseActivity {

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_grade);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
         initHead("我的等级");
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
