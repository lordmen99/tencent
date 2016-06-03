package com.mb.picvisionlive.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.SearchBean;
import com.tencent.qcloud.suixinbo.adapters.CommonAdapter;
import com.tencent.qcloud.suixinbo.utils.ViewHolder;

import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class SearchAdapter extends CommonAdapter<SearchBean>{
    public SearchAdapter(Context context, List<SearchBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, SearchBean item, int position) {
        TextView tv_guanzhu = helper.getView(R.id.tv_guanzhu);
        if (position==3||position==4){
            tv_guanzhu.setText("+关注");
            tv_guanzhu.setTextColor(Color.parseColor("#FF6B6B"));
            tv_guanzhu.setBackgroundResource(R.mipmap.search_btn_b);
        }
    }
}
