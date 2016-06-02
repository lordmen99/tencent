package com.mb.picvisionlive;

import android.content.Context;
import android.widget.ListView;

import com.mb.picvisionlive.adapter.PersonAdapter;
import com.mb.picvisionlive.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyLookActivity extends BaseActivity {
    Context context=MyLookActivity.this;
    List<PersonBean> list = new ArrayList<PersonBean>();
    PersonAdapter mPersonAdapter;

    @Bind(R.id.my_look_list)
    ListView myLookList;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_look);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("关注的人", true);
    }

    @Override
    public void bodymethod() {
        getDatas();
    }
    private void getDatas() {
        for (int i = 0; i < 18; i++) {
            PersonBean person = new PersonBean();
            person.setImg_id(i);

            list.add(person);
        }
        mPersonAdapter=new PersonAdapter(context,list);
        myLookList.setAdapter(mPersonAdapter);
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }



    @OnClick(R.id.commom_right_img)
    public void onClick() {
    }
}
