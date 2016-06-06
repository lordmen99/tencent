package com.mb.picvisionlive;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CloseAccountActivity extends BaseActivity {

    @Bind(R.id.close_account_result_txt)
    TextView mResultTxt;
    @Bind(R.id.close_account_search_edit)
    EditText mSearch_edit;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_close_account);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("封号系统");
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


    @OnClick(R.id.close_account_search_btn)
    public void onClick() {
        if (mSearch_edit.getText().toString().trim().length() == 0 ) {
            Toast.makeText(CloseAccountActivity.this, "ID号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mResultTxt.setText("您没有被封号");
    }
}
