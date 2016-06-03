package com.mb.picvisionlive.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenm on 2016/5/26.
 */
public class MoreDarenAdapter extends BaseAdapter{
    Context context;
    List<PersonBean> list = new ArrayList<PersonBean>();
    public MoreDarenAdapter(android.content.Context context, List<PersonBean> list){
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
            paramView= LayoutInflater.from(context).inflate(R.layout.item_more_daren,null);
            holder.head=(ImageView)paramView.findViewById(R.id.more_daren_head_img);
            holder.look=(TextView)paramView.findViewById(R.id.more_daren_look_text);
            paramView.setTag(holder);
        }
        if (list.get(i).getImg_id()%2==0) {
            holder.head.setImageResource(R.mipmap.toux1);
            holder.look.setBackgroundResource(R.drawable.more_daren_corner_red);
            holder.look.setTextColor(Color.parseColor("#FFFFFF"));
            holder.look.setText("＋关注");
        }else{
            holder.head.setImageResource(R.mipmap.toux);
            holder.look.setBackgroundResource(R.drawable.more_daren_corner_stroke);
            holder.look.setTextColor(Color.parseColor("#FF6B6B"));
            holder.look.setText("已关注");
        }

        return paramView;
    }
    class ViewHolder{
        ImageView head;
        TextView look;
    }
}
