package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;

import com.mb.picvisionlive.adapter.AddressAdapter;
import com.mb.picvisionlive.bean.AddressBean;
import com.mb.picvisionlive.weight.SwipeListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity {
    Context context = AddressActivity.this;
    AddressAdapter mAddressAdapter;
    @Bind(R.id.address_list)
    SwipeListView addressList;

    List<AddressBean> list = new ArrayList<AddressBean>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("地址管理");
    }

    @Override
    public void bodymethod() {
        getData();
    }

    private void getData() {
        for (int i = 0; i < 3; i++) {
            AddressBean bean = new AddressBean();
            bean.setName("空间" + i);
            list.add(bean);
        }
        mAddressAdapter = new AddressAdapter(context, list);
        addressList.setAdapter(mAddressAdapter);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }



    @OnClick(R.id.address_add_btn)
    public void onClick() {
        Intent intent = new Intent(context, ADDAddressActivity.class);
        startActivity(intent);

    }
}
