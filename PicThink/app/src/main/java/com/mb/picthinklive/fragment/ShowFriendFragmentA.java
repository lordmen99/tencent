package com.mb.picthinklive.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mb.picthinklive.R;
import com.mb.picthinklive.adapter.goodsFriendAdapter;
import com.mb.picthinklive.bean.goodsFriendBean;

import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class ShowFriendFragmentA extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lv_goods_friend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_show_friends_a, container, false);
        lv_goods_friend = (ListView) rootView.findViewById(R.id.lv_goods_friend);
        lv_goods_friend.setOnItemClickListener(this);
        initListView();

        return rootView;
    }

    private void initListView() {
        List<goodsFriendBean> list = goodsFriendBean.getList();
        goodsFriendAdapter adapter = new goodsFriendAdapter(getActivity(),list,R.layout.frag_friend_item);
        lv_goods_friend.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ChatDetailDialog dialog = new ChatDetailDialog();
    }
}
