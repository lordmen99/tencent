package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.mb.picvisionlive.adapter.AddressAdapter;
import com.mb.picvisionlive.bean.AddressBean;
import com.mb.picvisionlive.tools.ScreenOnclickCallback;
import com.mb.picvisionlive.weight.SwipeListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddressActivity extends BaseActivity implements ScreenOnclickCallback {
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
        list.clear();
        for (int i = 0; i < 13; i++) {
            AddressBean bean = new AddressBean();
            bean.setName("空间" + i);
            list.add(bean);
        }
        View view= LayoutInflater.from(context).inflate(R.layout.address_part_foot,null);
        Button addBtn=(Button)view.findViewById(R.id.address_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ADDAddressActivity.class);
                startActivity(intent);
            }
        });
        addressList.addFooterView(view,null,false);
        mAddressAdapter = new AddressAdapter(context, list,this);
        addressList.setAdapter(mAddressAdapter);

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }





    @Override
    public void screenOnclick(int arg1) {
        list.remove(arg1);
        mAddressAdapter.notifyDataSetChanged();
    }
}
