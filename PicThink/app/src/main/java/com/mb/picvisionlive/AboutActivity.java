package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

Context context=AboutActivity.this;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("关于我们");
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }



    @OnClick({R.id.about_convention_line, R.id.about_private_line, R.id.about_service_line, R.id.about_contact_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_convention_line:{
                Intent intent =new Intent(context, ConventionActivity.class);
                startActivity(intent);
                break;}
            case R.id.about_private_line:{
                Intent intent =new Intent(context, ConventionActivity.class);
                startActivity(intent);
                break;}
            case R.id.about_service_line:{
                Intent intent =new Intent(context, ConventionActivity.class);
                startActivity(intent);
                break;}
            case R.id.about_contact_line:{
                Intent intent =new Intent(context, ContactsUsActivity.class);
                startActivity(intent);
                break;}
        }
    }
}
