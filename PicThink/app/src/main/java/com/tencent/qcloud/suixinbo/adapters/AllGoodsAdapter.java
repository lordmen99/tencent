package com.tencent.qcloud.suixinbo.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.AllGoodsBean;
import com.tencent.qcloud.suixinbo.utils.ViewHolder;

import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class AllGoodsAdapter extends CommonAdapter<AllGoodsBean>{
    public AllGoodsAdapter(Context context, List<AllGoodsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, AllGoodsBean item, int position) {
        TextView tv_goodsname = helper.getView(R.id.tv_goodsname);
        tv_goodsname.setText(item.getGoodsname());
        TextView tv_goodsprice = helper.getView(R.id.tv_goodsprice);
        tv_goodsprice.setText(item.getGoodsprice());
        ImageView iv_goodsurl = helper.getView(R.id.iv_goodsurl);
        TextView tv_add = helper.getView(R.id.tv_add);
        tv_add.setVisibility(View.VISIBLE);
    }
}
