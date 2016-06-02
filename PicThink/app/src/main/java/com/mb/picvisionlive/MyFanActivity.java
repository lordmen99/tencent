package com.mb.picvisionlive;

import android.content.Context;
import android.widget.ListView;

import com.mb.picvisionlive.adapter.PersonAdapter;
import com.mb.picvisionlive.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyFanActivity extends BaseActivity {
    Context context=MyFanActivity.this;
    List<PersonBean> list = new ArrayList<PersonBean>();
    PersonAdapter mPersonAdapter;
    @Bind(R.id.my_fan_list)
    ListView myFanList;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_fan);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("粉丝");
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
        myFanList.setAdapter(mPersonAdapter);
    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


}
