package com.mb.picvisionlive;

import android.widget.EditText;

import com.mb.picvisionlive.setting.PicConstants;

import org.simple.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditJobActivity extends BaseActivity {



    @Bind(R.id.edit_job_edit)
    EditText mJobEdit;

    String job="";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_job);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("职业", "保存");
        mJobEdit.setText(getIntent().getStringExtra("job"));
    }

    @Override
    public void bodymethod() {

    }



    @OnClick(R.id.commom_right_txt)
    public void onClick() {
        job=mJobEdit.getText().toString();
        EventBus.getDefault().post(job, PicConstants.JOB_TAG);

        finish();
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
