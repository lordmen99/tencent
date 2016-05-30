package com.mb.picvisionlive;

import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNickActivity extends BaseActivity {


    @Bind(R.id.edit_nick_edit)
    EditText editNickEdit;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_nick);
        ButterKnife.bind(this);
        //EventBus.getDefault().register(this);
    }

    @Override
    public void findViewByid() {
        initHead("昵称", "保存");
    }

    @Override
    public void bodymethod() {

    }



    @OnClick(R.id.commom_right_txt)
    public void onClick() {
        finish();
    }
}
