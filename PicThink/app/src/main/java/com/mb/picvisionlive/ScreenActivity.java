package com.mb.picvisionlive;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mb.picvisionlive.adapter.ScreenListAdapter;
import com.mb.picvisionlive.bean.RegionBean;
import com.mb.picvisionlive.tools.ScreenOnclickCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenActivity extends BaseActivity implements ScreenOnclickCallback {
    ScreenListAdapter mScreenListAdapter;
    Context context = ScreenActivity.this;
    List<RegionBean> list = new ArrayList<RegionBean>();

    @Bind(R.id.single_famale_img)
    ImageView singleFamaleImg;
    @Bind(R.id.single_famale_heart_img)
    ImageView singleFamaleHeartImg;
    @Bind(R.id.single_famale_txt)
    TextView singleFamaleTxt;
    @Bind(R.id.screen_all_img)
    ImageView screenAllImg;
    @Bind(R.id.screen_all_heart_img)
    ImageView screenAllHeartImg;
    @Bind(R.id.screen_all_txt)
    TextView screenAllTxt;
    @Bind(R.id.single_male_img)
    ImageView singleMaleImg;
    @Bind(R.id.single_male_heart_img)
    ImageView singleMaleHeartImg;
    @Bind(R.id.single_male_txt)
    TextView singleMaleTxt;
    @Bind(R.id.screen_list)
    ListView screenList;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_screen);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        mScreenListAdapter = new ScreenListAdapter(context, list, this);
        screenList.setAdapter(mScreenListAdapter);
    }

    @Override
    public void bodymethod() {
        getDatas();
    }

    private void getDatas() {
        for (int i = 0; i < 18; i++) {
            RegionBean bean = new RegionBean();
            bean.setNumber(200 + i);

            list.add(bean);
        }
//        mScreenListAdapter=new ScreenListAdapter(context,list);
//        screenList.setAdapter(mScreenListAdapter);
        mScreenListAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


    @OnClick({R.id.single_famale_img, R.id.screen_all_img, R.id.single_male_img})
    public void onClick(View view) {
        //            singleFamaleImg screenAllImg singleMaleImg
//            singleFamaleHeartImg screenAllHeartImg singleMaleHeartImg
//            singleFamaleTxt screenAllTxt singleMaleTxt
        singleFamaleImg.setImageResource(R.mipmap.screen_famale_a);
        screenAllImg.setImageResource(R.mipmap.screen_all_of_weixuan);
        singleMaleImg.setImageResource(R.mipmap.screen_male_a);

        singleFamaleHeartImg.setImageResource(R.mipmap.screen_heart_weixuan);
        screenAllHeartImg.setImageResource(R.mipmap.screen_heart_weixuan);
        singleMaleHeartImg.setImageResource(R.mipmap.screen_heart_weixuan);

        singleFamaleTxt.setTextColor(Color.parseColor("#AAAAAA"));//#F8D150
        screenAllTxt.setTextColor(Color.parseColor("#AAAAAA"));
        singleMaleTxt.setTextColor(Color.parseColor("#AAAAAA"));

        switch (view.getId()) {
            case R.id.single_famale_img: {
                singleFamaleImg.setImageResource(R.mipmap.screen_famale_a_xuanzhong);
                singleFamaleHeartImg.setImageResource(R.mipmap.screen_heart_yixuan);
                singleFamaleTxt.setTextColor(Color.parseColor("#F8D150"));//#F8D150
                break;
            }
            case R.id.screen_all_img: {
                screenAllImg.setImageResource(R.mipmap.screen_all_of);
                screenAllHeartImg.setImageResource(R.mipmap.screen_heart_yixuan);
                screenAllTxt.setTextColor(Color.parseColor("#F8D150"));
                break;
            }
            case R.id.single_male_img: {
                singleMaleImg.setImageResource(R.mipmap.screen_male_a_xuanzhong);
                singleMaleImg.setImageResource(R.mipmap.screen_heart_yixuan);
                singleMaleTxt.setTextColor(Color.parseColor("#F8D150"));
                break;
            }
        }
    }

    @Override
    public void screenOnclick(int arg1) {
        RegionBean bean = list.get(arg1);
        bean.setSelect(true);
        mScreenListAdapter.notifyDataSetChanged();
    }



    @OnClick(R.id.scrern_finish_txt)
    public void onClick() {
        finish();
    }
}
