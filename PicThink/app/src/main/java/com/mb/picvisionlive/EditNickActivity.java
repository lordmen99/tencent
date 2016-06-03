package com.mb.picvisionlive;

import android.widget.EditText;

import com.mb.picvisionlive.setting.PicConstants;

import org.simple.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditNickActivity extends BaseActivity {


    @Bind(R.id.edit_nick_edit)
    EditText editNickEdit;
    String nickName="";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_nick);
        ButterKnife.bind(this);

    }

    @Override
    public void findViewByid() {
        initHead("昵称", "保存");
        editNickEdit.setText(getIntent().getStringExtra("nick"));
    }

    @Override
    public void bodymethod() {

    }



    @OnClick(R.id.commom_right_txt)
    public void onClick() {
        nickName=editNickEdit.getText().toString();
        EventBus.getDefault().post(nickName, PicConstants.NICK_TAG);

        finish();
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
