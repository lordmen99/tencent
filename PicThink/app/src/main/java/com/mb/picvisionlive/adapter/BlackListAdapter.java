package com.mb.picvisionlive.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.PersonBean;
import com.mb.picvisionlive.tools.ScreenOnclickCallback;
import com.mb.picvisionlive.weight.SwipeItemLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenm on 2016/5/26.
 */
public class BlackListAdapter extends BaseAdapter{
    Activity activity;
    Context context;
    private ScreenOnclickCallback mScreenOnclickCallback;
    List<PersonBean> list = new ArrayList<PersonBean>();
    public BlackListAdapter(Context context, List<PersonBean> list,Activity activity){
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
            View view01 = LayoutInflater.from(context).inflate(R.layout.item_black_list, null);
            View view02 = LayoutInflater.from(context).inflate(R.layout.item_address_layout2, null);
            paramView = new SwipeItemLayout(view01, view02, null, null);

            holder.head=(ImageView)paramView.findViewById(R.id.item_black_list_head_img);
            holder.del_txt=(TextView)paramView.findViewById(R.id.del_txt);
            paramView.setTag(holder);
        }
        if (list.get(i).getImg_id()%2==0) {
            holder.head.setImageResource(R.mipmap.toux1);
        }else{
            holder.head.setImageResource(R.mipmap.toux);
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
        ImageView head;
        TextView del_txt;
    }
}
