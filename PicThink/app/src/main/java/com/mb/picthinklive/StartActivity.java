package com.mb.picthinklive;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.suixinbo.views.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends BaseActivity {

    @Bind(R.id.start_weibo_img)
    ImageView startWeiboImg;
    @Bind(R.id.start_weixin_img)
    ImageView startWeixinImg;
    @Bind(R.id.start_qq_img)
    ImageView startQqImg;
    @Bind(R.id.start_mobile_img)
    ImageView startMobileImg;
    @Bind(R.id.start_agreement_txt)
    TextView startAgreementTxt;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);


    }

    @Override
    public void findViewByid() {
        initView();

    }

    private void initView() {
        String str1 = "登录即代表你同意";
        String str2 = "色拉服务和隐私条款";
        String str = str1 + str2;

        SpannableString spanString = new SpannableString(str);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.WHITE);
        spanString.setSpan(span, 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        UnderlineSpan spanUnder = new UnderlineSpan();
        spanString.setSpan(spanUnder, str1.length(), str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        spanString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(StartActivity.this, AgreementActivity.class);
                startActivity(intent);
            }
            // 表示点击text的长度都有效触发这个事件
        }, str1.length(), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan spanForeColor = new ForegroundColorSpan(Color.parseColor("#C89F40"));
        spanString.setSpan(spanForeColor, str1.length(), str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        startAgreementTxt .setHighlightColor(getResources().getColor(android.R.color.transparent));
        startAgreementTxt.append(spanString);
        startAgreementTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void bodymethod() {

    }

    @OnClick({R.id.start_weibo_img, R.id.start_weixin_img, R.id.start_qq_img, R.id.start_mobile_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_weibo_img:{
                break;}
            case R.id.start_weixin_img:{
                break;}
            case R.id.start_qq_img:{
                Intent intent = new Intent(StartActivity.this,com.tencent.qcloud.suixinbo.views.LoginActivity.class);//PicLoginActivity
                startActivity(intent);
                break;}
            case R.id.start_mobile_img:{
                Intent intent = new Intent(StartActivity.this,SetHeadActivity.class);//PicLoginActivity
                startActivity(intent);
                break;}
        }
    }


}
