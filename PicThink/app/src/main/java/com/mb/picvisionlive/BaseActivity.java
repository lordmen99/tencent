package com.mb.picvisionlive;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.qcloud.suixinbo.QavsdkApplication;


public abstract class BaseActivity extends FragmentActivity {

    protected Context appContext;
    protected Context mContext;
//ss

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        appContext = getApplicationContext();
        mContext = this;
        QavsdkApplication.getInstance().addActivity(this);

        setContentView();
        findViewByid();
        bodymethod();

    }

    public abstract void setContentView(); // 绑定XML界面

    public abstract void findViewByid(); // 实例化控件初始化值

    public abstract void bodymethod(); // 在OnCreate中实现各种方法

    /**
     * 初始化只有返回标头和标题的头部
     *
     * @param title 标题
     **/
    public void initHead(String title) {

        RelativeLayout view = (RelativeLayout) findViewById(R.id.common_top_rel);

        ImageView back = (ImageView) view.findViewById(R.id.common_back_img);
        TextView label = (TextView) view.findViewById(R.id.commom_title_txt);


        back.setOnClickListener(headback);
        label.setText(title);

    }
    /**
     * 初始化只有返回标头和标题的头部
     *
     * @param title 标题
     **/
    public void initHead(String title,String rightTxt) {

        RelativeLayout view = (RelativeLayout) findViewById(R.id.common_top_rel);

        ImageView back = (ImageView) view.findViewById(R.id.common_back_img);
        TextView label = (TextView) view.findViewById(R.id.commom_title_txt);
        TextView right_txt = (TextView) view.findViewById(R.id.commom_right_txt);
        right_txt.setText(rightTxt);

        back.setOnClickListener(headback);
        label.setText(title);

    }
    /**
     * 初始化只有返回标头和标题的头部
     *
     * @param title 标题
     **/
    public void initHead(String title,boolean isAdd) {

        RelativeLayout view = (RelativeLayout) findViewById(R.id.common_top_rel);

        ImageView back = (ImageView) view.findViewById(R.id.common_back_img);
        TextView label = (TextView) view.findViewById(R.id.commom_title_txt);
        ImageView right_img = (ImageView) view.findViewById(R.id.commom_right_img);
        if (isAdd){
            right_img.setVisibility(View.VISIBLE);
        }

        back.setOnClickListener(headback);
        label.setText(title);

    }
    public OnClickListener headback = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            finish();

        }
    };

    @Override
    protected void onDestroy() {
        QavsdkApplication.getInstance().removeActivity(this);
        super.onDestroy();

    }
}
