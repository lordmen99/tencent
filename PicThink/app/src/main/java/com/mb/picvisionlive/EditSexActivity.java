package com.mb.picvisionlive;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mb.picvisionlive.setting.PicConstants;

import org.simple.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditSexActivity extends BaseActivity {

    @Bind(R.id.edit_sex_boy_img)
    ImageView mBoyImg;
    @Bind(R.id.edit_sex_girl_img)
    ImageView mGirlImg;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_sex);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("性别");
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

    @OnClick({R.id.edit_sex_boy_line, R.id.edit_sex_girl_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_sex_boy_line:
                mBoyImg.setVisibility(View.VISIBLE);
                mGirlImg.setVisibility(View.GONE);
                EventBus.getDefault().post("男", PicConstants.SEX_TAG);
                finish();
                break;
            case R.id.edit_sex_girl_line:
                mBoyImg.setVisibility(View.GONE);
                mGirlImg.setVisibility(View.VISIBLE);
                EventBus.getDefault().post("女", PicConstants.SEX_TAG);
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
