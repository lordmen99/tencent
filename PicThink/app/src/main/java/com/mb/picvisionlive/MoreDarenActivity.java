package com.mb.picvisionlive;

import android.content.Context;
import android.widget.ListView;

import com.mb.picvisionlive.adapter.MoreDarenAdapter;
import com.mb.picvisionlive.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoreDarenActivity extends BaseActivity {
    Context context=MoreDarenActivity.this;
    List<PersonBean> list = new ArrayList<PersonBean>();
    MoreDarenAdapter mMoreDarenAdapter;


    @Bind(R.id.more_daren_list)
    ListView moreDarenList;
//    @Bind(R.id.swipe_refresh_layout_list)
//    SwipeRefreshLayout swipeRefreshLayoutList;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_more_daren);
        ButterKnife.bind(this);

        getDatas();
    }

    private void getDatas() {
        for (int i = 0; i < 18; i++) {
            PersonBean person = new PersonBean();
            person.setImg_id(i);

            list.add(person);
        }
        mMoreDarenAdapter=new MoreDarenAdapter(context,list);
        moreDarenList.setAdapter(mMoreDarenAdapter);
    }

    @Override
    public void findViewByid() {
        initHead("达人");
        //swipeRefreshLayoutList.setRefreshing(false);
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


}
