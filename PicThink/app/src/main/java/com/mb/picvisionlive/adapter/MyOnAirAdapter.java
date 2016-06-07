package com.mb.picvisionlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.tencent.qcloud.suixinbo.model.LiveInfoJson;

import java.util.ArrayList;
import java.util.List;


/**
 * 直播列表的Adapter
 */
public class MyOnAirAdapter extends BaseAdapter {
    Context context;
    List<LiveInfoJson> list = new ArrayList<LiveInfoJson>();
    public MyOnAirAdapter(Context context, List<LiveInfoJson> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View paramView;
        ViewHolder holder;
        if (view != null) {
            paramView=view;
            holder=(ViewHolder) paramView.getTag();
        }else {
            holder=new ViewHolder();
            paramView = LayoutInflater.from(context).inflate(R.layout.item_my_onair, null);

            holder.onair_members=(TextView) paramView.findViewById(R.id.onair_members);


            paramView.setTag(holder);
        }
        holder.onair_members.setText(list.get(i).getWatchCount()+"");

        return paramView;
    }
    class ViewHolder{

        TextView onair_members;
    }
}
