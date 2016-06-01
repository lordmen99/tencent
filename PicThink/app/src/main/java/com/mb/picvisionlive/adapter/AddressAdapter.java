package com.mb.picvisionlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.AddressBean;
import com.mb.picvisionlive.weight.SwipeItemLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenm on 2016/5/26.
 */
public class AddressAdapter extends BaseAdapter{
    Context context;
    List<AddressBean> list = new ArrayList<AddressBean>();
    public AddressAdapter(android.content.Context context, List<AddressBean> list){
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
            View view01 = LayoutInflater.from(context).inflate(R.layout.item_address_layout1, null);
            View view02 = LayoutInflater.from(context).inflate(R.layout.item_address_layout2, null);
            paramView = new SwipeItemLayout(view01, view02, null, null);

            paramView.setTag(holder);
        }
//        if (list.get(i).getImg_id()%2==0) {
//            holder.head.setImageResource(R.mipmap.toux1);
//        }else{
//            holder.head.setImageResource(R.mipmap.toux);
//        }

        return paramView;
    }
    class ViewHolder{
        ImageView head;
    }
}
