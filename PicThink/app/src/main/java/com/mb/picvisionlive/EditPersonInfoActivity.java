package com.mb.picvisionlive;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.commom_right_txt, R.id.edit_person_info_head_line, R.id.edit_person_info_nick_line, R.id.edit_person_info_ID_line, R.id.edit_person_info_sex_line, R.id.edit_person_info_sign_line, R.id.edit_person_info_check_line, R.id.edit_person_info_age_line, R.id.edit_person_info_home_line, R.id.edit_person_info_job_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commom_right_txt:
                break;
            case R.id.edit_person_info_head_line:
                break;
            case R.id.edit_person_info_nick_line:
                break;
            case R.id.edit_person_info_ID_line:
                break;
            case R.id.edit_person_info_sex_line:
                break;
            case R.id.edit_person_info_sign_line:
                break;
            case R.id.edit_person_info_check_line:
                break;
            case R.id.edit_person_info_age_line:
                break;
            case R.id.edit_person_info_home_line:
                break;
            case R.id.edit_person_info_job_line:
                break;
        }
    }
}
