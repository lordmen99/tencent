package com.mb.picvisionlive.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.SellBean;
import com.tencent.qcloud.suixinbo.adapters.CommonAdapter;
import com.tencent.qcloud.suixinbo.utils.ViewHolder;

import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class SellAdapter extends CommonAdapter<SellBean>{
    public SellAdapter(Context context, List<SellBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SellBean item, int position) {
        TextView tv_goodsname = helper.getView(R.id.tv_goodscontent);
        TextView tv_sell = helper.getView(R.id.tv_sell);
        String goodsCount="已购买 "+item.getGoodsCount()+" 件";
        SpannableStringBuilder style=new SpannableStringBuilder(goodsCount);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#50D2F8")),3, goodsCount.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_sell.setText(style);

        TextView tv_buy = helper.getView(R.id.tv_buy);
        String goodsGain="已赚金币 "+item.getGoodsGain() +" 金币";
        SpannableStringBuilder style2=new SpannableStringBuilder(goodsGain);
        style2.setSpan(new ForegroundColorSpan(Color.parseColor("#FD8265")),4, goodsGain.length()-2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_buy.setText(style2);

        tv_goodsname.setText(item.getGoodsName());
    }
}
