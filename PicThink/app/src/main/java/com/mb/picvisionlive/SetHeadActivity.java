package com.mb.picvisionlive;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetHeadActivity extends BaseActivity {


    @Bind(R.id.set_head_head_img)
    ImageView setHeadHeadImg;
    @Bind(R.id.set_head_nickname_edit)
    EditText setHeadNicknameEdit;
    @Bind(R.id.set_head_boy_checkbox)
    CheckBox setHeadBoyCheckbox;
    @Bind(R.id.set_head_boy_line)
    LinearLayout setHeadBoyLine;
    @Bind(R.id.set_head_girl_checkbox)
    CheckBox setHeadGirlCheckbox;
    @Bind(R.id.set_head_girl_line)
    LinearLayout setHeadGirlLine;
    @Bind(R.id.set_head_save_btn)
    Button setHeadSaveBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_set_head);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("设置头像");
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

    @OnClick({R.id.set_head_head_img, R.id.set_head_boy_line, R.id.set_head_girl_line, R.id.set_head_save_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_head_head_img:
                break;
            case R.id.set_head_boy_line:
                setHeadBoyCheckbox.setChecked(true);
                setHeadGirlCheckbox.setChecked(false);
                break;
            case R.id.set_head_girl_line:
                setHeadGirlCheckbox.setChecked(true);
                setHeadBoyCheckbox.setChecked(false);
                break;
            case R.id.set_head_save_btn:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
