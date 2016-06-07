package com.mb.picvisionlive.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.AddressBean;
import com.mb.picvisionlive.tools.ScreenOnclickCallback;
import com.mb.picvisionlive.weight.SwipeItemLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenm on 2016/5/26.
 */
public class AddressAdapter extends BaseAdapter{
    Context context;
    Activity activity;
    List<AddressBean> list = new ArrayList<AddressBean>();
    private ScreenOnclickCallback mScreenOnclickCallback;
    public AddressAdapter(android.content.Context context, List<AddressBean> list, Activity activity){
        this.context=context;
        this.list=list;
        this.activity=activity;
        try {
            mScreenOnclickCallback = (ScreenOnclickCallback) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new RuntimeException("activity impl ScreenOnclickCallback error");
        }
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
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
            holder.name=(TextView) paramView.findViewById(R.id.item_address_name_txt);
            holder.default_txt=(TextView) paramView.findViewById(R.id.item_default_txt);
            holder.del_txt=(TextView) paramView.findViewById(R.id.del_txt);
            paramView.setTag(holder);
        }
        holder.name.setText(list.get(i).getName());
        if (i==0) {
            holder.default_txt.setText("【默认】");
        }else{
            holder.default_txt.setText("");
        }
        holder.del_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScreenOnclickCallback.screenOnclick(i);
            }
        });
        return paramView;
    }
    class ViewHolder{

        TextView name,default_txt,del_txt;
    }
}
