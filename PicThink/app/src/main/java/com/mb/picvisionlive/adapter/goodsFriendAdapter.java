package com.mb.picvisionlive.adapter;

import android.content.Context;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.goodsFriendBean;
import com.tencent.qcloud.suixinbo.adapters.CommonAdapter;
import com.tencent.qcloud.suixinbo.utils.ViewHolder;

import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class goodsFriendAdapter extends CommonAdapter<goodsFriendBean>{
    public goodsFriendAdapter(Context context, List<goodsFriendBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, goodsFriendBean item, int position) {
        TextView tv_nickname = helper.getView(R.id.tv_nickname);
        tv_nickname.setText(item.getNickname());
        TextView tv_content = helper.getView(R.id.tv_content);
        tv_content.setText(item.getMessage());
    }
}
