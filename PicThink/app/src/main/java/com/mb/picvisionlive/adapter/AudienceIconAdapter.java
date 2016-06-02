package com.mb.picvisionlive.adapter;

import android.content.Context;

import com.mb.picvisionlive.bean.AudienceIconBean;
import com.tencent.qcloud.suixinbo.adapters.CommonAdapter;
import com.tencent.qcloud.suixinbo.utils.ViewHolder;

import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class AudienceIconAdapter extends CommonAdapter<AudienceIconBean>{
    public AudienceIconAdapter(Context context, List<AudienceIconBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }



    @Override
    public void convert(ViewHolder helper, AudienceIconBean item, int position) {

    }
}
