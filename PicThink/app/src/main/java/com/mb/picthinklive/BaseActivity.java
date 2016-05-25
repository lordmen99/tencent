package com.mb.picthinklive;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public abstract class BaseActivity extends FragmentActivity {

    protected Context appContext;
    protected Context mContext;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        appContext = getApplicationContext();
        mContext = this;


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


    public OnClickListener headback = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            finish();

        }
    };


}
