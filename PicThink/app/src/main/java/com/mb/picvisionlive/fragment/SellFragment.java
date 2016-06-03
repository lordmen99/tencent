package com.mb.picvisionlive.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.adapter.SellAdapter;
import com.mb.picvisionlive.bean.SellBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class SellFragment extends Fragment {
    @Bind(R.id.lv_sell)
    ListView lvSell;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_sell, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        List<SellBean> sellBeanList = SellBean.getList();
        SellAdapter adapter = new SellAdapter(getActivity(),sellBeanList,R.layout.item_sell);
        lvSell.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
