package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    Context context = SettingActivity.this;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("设置");
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


    @OnClick({R.id.setting_safe_line, R.id.setting_blacklist_line, R.id.setting_send_line, R.id.setting_cache_line, R.id.setting_about_line, R.id.setting_suggest_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_safe_line: {
                Intent intent = new Intent(context, AccountSafeActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting_blacklist_line: {
                Intent intent = new Intent(context, BlackListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting_send_line: {
                Intent intent = new Intent(context, PushMessageActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting_cache_line: {
                break;
            }
            case R.id.setting_about_line: {
                Intent intent = new Intent(context, AboutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting_suggest_line: {
                Intent intent = new Intent(context, HelpActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
