package com.mb.picvisionlive;

import android.widget.EditText;

import com.mb.picvisionlive.setting.PicConstants;

import org.simple.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditSignActivity extends BaseActivity {


    @Bind(R.id.edit_sign_edit)
    EditText mSignEdit;

    String sign="";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_sign);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
      initHead("个性签名","保存");
    }

    @Override
    public void bodymethod() {

    }



    @OnClick(R.id.commom_right_txt)
    public void onClick() {
        sign=mSignEdit.getText().toString();
        EventBus.getDefault().post(sign, PicConstants.SIGN_TAG);

        finish();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
