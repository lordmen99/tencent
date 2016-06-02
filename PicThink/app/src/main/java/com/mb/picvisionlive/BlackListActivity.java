package com.mb.picvisionlive;

import android.content.Context;

import com.mb.picvisionlive.adapter.BlackListAdapter;
import com.mb.picvisionlive.bean.PersonBean;
import com.mb.picvisionlive.weight.SwipeListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BlackListActivity extends BaseActivity {
    Context context = BlackListActivity.this;
    List<PersonBean> list = new ArrayList<PersonBean>();
    BlackListAdapter mBlackListAdapter;
    @Bind(R.id.black_list)
    SwipeListView blackList;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_black_list);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("黑名单");
    }

    @Override
    public void bodymethod() {

        getData();
    }

    private void getData() {
        for (int i = 0; i < 13; i++) {
            PersonBean bean = new PersonBean();
            bean.setName("黑名单" + i);
            list.add(bean);
        }
        mBlackListAdapter = new BlackListAdapter(context, list);
        blackList.setAdapter(mBlackListAdapter);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


}
