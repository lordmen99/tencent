package com.mb.picvisionlive;

import butterknife.ButterKnife;

public class EditPersonInfoActivity extends BaseActivity {


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_person_info);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("编辑资料");
    }

    @Override
    public void bodymethod() {

    }
}
