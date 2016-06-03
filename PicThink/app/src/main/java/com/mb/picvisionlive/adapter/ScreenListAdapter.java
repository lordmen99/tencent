package com.mb.picvisionlive.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.RegionBean;
import com.mb.picvisionlive.tools.ScreenOnclickCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenm on 2016/5/26.
 */
public class ScreenListAdapter extends BaseAdapter{
    Context context;
    Activity activity;
    List<RegionBean> list = new ArrayList<RegionBean>();
    private ScreenOnclickCallback mScreenOnclickCallback;
    public ScreenListAdapter(Context context, List<RegionBean> list, Activity activity){
        this.context=context;
        this.list=list;
        this.activity=activity;

        try {
            mScreenOnclickCallback = (ScreenOnclickCallback) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new RuntimeException("activity impl employeeOnclickCallback error");
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
            paramView= LayoutInflater.from(context).inflate(R.layout.item_screen,null);
            holder.screen_count_txt=(TextView)paramView.findViewById(R.id.screen_count_txt);
            holder.screen_xuanz_img=(ImageView)paramView.findViewById(R.id.screen_xuanz_img);
            holder.screen_line=(LinearLayout)paramView.findViewById(R.id.screen_line);
            paramView.setTag(holder);
        }
        holder.screen_count_txt.setText(list.get(i).getNumber()+"");
        if (list.get(i).isSelect()) {
            holder.screen_xuanz_img.setVisibility(View.VISIBLE);
        }else{
            holder.screen_xuanz_img.setVisibility(View.INVISIBLE);
        }
        holder.screen_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScreenOnclickCallback.screenOnclick(i);
            }
        });
        return paramView;
    }
    class ViewHolder{
        TextView screen_count_txt;
        ImageView screen_xuanz_img;
        LinearLayout screen_line;
    }
}
