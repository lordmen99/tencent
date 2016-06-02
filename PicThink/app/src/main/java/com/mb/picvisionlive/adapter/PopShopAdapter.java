package com.mb.picvisionlive.adapter;

import android.content.Context;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.ShopBean;
import com.tencent.qcloud.suixinbo.adapters.CommonAdapter;
import com.tencent.qcloud.suixinbo.utils.ViewHolder;

import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class PopShopAdapter extends CommonAdapter<ShopBean>{
    public PopShopAdapter(Context context, List<ShopBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, ShopBean item, int position) {
        TextView tv_goodsname = helper.getView(R.id.tv_goodsname);
        tv_goodsname.setText(item.getGoodsName());
        TextView tv_goodscontent = helper.getView(R.id.tv_goodscontent);
        tv_goodscontent.setText(item.getGoodsContent());
        TextView tv_goodsprice = helper.getView(R.id.tv_goodsprice);
        tv_goodsprice.setText(item.getGoodsPrice());
    }
}
