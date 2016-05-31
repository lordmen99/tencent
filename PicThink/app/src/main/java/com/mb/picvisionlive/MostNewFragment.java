package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mb.picvisionlive.adapter.HeadAdapter;
import com.mb.picvisionlive.bean.PersonBean;
import com.mb.picvisionlive.weight.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/1/13.
 */
public class MostNewFragment extends Fragment {
    HeadAdapter mheadDarenAdapter;
    HeadAdapter mheadNewOnairAdapter;
    Context context;
    @Bind(R.id.fragment_most_new_daren_gridview)
    MyGridView fragmentMostNewDarenGridview;
    @Bind(R.id.fragment_most_new_onair_gridview)
    MyGridView fragmentMostNewOnairGridview;

    @Bind(R.id.fragment_most_new_daren_line)
    LinearLayout mdaren_line;


    List<PersonBean> darenList = new ArrayList<PersonBean>();
    List<PersonBean> newOnairList = new ArrayList<PersonBean>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_most_new, null);
        ButterKnife.bind(this, view);


        initGridView();
        return view;
    }

    private void initGridView() {

        for (int i = 0; i < 18; i++) {
            PersonBean person = new PersonBean();
            person.setImg_id(i);
            if (i < 6) {
                darenList.add(person);
            }

            newOnairList.add(person);
        }

        mheadDarenAdapter = new HeadAdapter(context, darenList);
        mheadNewOnairAdapter = new HeadAdapter(context, newOnairList);
        fragmentMostNewDarenGridview.setAdapter(mheadDarenAdapter);
        fragmentMostNewOnairGridview.setAdapter(mheadNewOnairAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.fragment_most_new_daren_line)
    public void onClick() {
        Intent intent =new Intent(context, MoreDarenActivity.class);
        startActivity(intent);
    }
}
