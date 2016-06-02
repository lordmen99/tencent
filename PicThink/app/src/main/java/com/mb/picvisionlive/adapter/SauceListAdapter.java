package com.mb.picvisionlive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenm on 2016/5/26.
 */
public class SauceListAdapter extends BaseAdapter{
    Context context;
    List<RecordBean> list = new ArrayList<RecordBean>();
    public SauceListAdapter(Context context, List<RecordBean> list){
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
            paramView= LayoutInflater.from(context).inflate(R.layout.item_sauce_list,null);
            holder.sauce_salad_txt=(TextView)paramView.findViewById(R.id.sauce_salad_txt);
            paramView.setTag(holder);
        }
        holder.sauce_salad_txt.setText(list.get(i).getNumber()+"");
        return paramView;
    }
    class ViewHolder{
        TextView sauce_salad_txt;
    }
}
