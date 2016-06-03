package com.mb.picvisionlive;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatDetailActivity extends BaseActivity {


    @Bind(R.id.common_back_img)
    ImageView commonBackImg;
    @Bind(R.id.commom_title_txt)
    TextView commomTitleTxt;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_chat_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        commomTitleTxt.setText("欧阳美美");
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @OnClick({R.id.common_back_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_back_img:
                finish();
                break;
        }
    }
}
