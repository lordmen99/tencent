package com.mb.picthinklive.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mb.picthinklive.R;
import com.mb.picthinklive.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenm on 2016/5/26.
 */
public class HeadAdapter extends BaseAdapter{
    android.content.Context context;
    List<PersonBean> list = new ArrayList<PersonBean>();
    public  HeadAdapter(android.content.Context context,List<PersonBean> list){
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
            paramView= LayoutInflater.from(context).inflate(R.layout.item_head,null);
            holder.head=(ImageView)paramView.findViewById(R.id.item_head_img);
            paramView.setTag(holder);
        }
        if (list.get(i).getImg_id()%2==0) {
            holder.head.setImageResource(R.mipmap.toux1);
        }else{
            holder.head.setImageResource(R.mipmap.toux);
        }

        return paramView;
    }
    class ViewHolder{
        ImageView head;
    }
}
