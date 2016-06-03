package com.mb.picvisionlive;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mb.picvisionlive.adapter.SearchAdapter;
import com.mb.picvisionlive.bean.SearchBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {



    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.iv_clear)
    ImageView ivClear;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.lv_search)
    ListView lvSearch;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        etSearch.addTextChangedListener(textWatcher);
    }

    @Override
    public void bodymethod() {

    }

    @OnClick({R.id.tv_search,R.id.iv_clear})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_search:
                finish();
                break;
            case R.id.iv_clear:
               etSearch.setText("");
                break;
        }
    }
    private TextWatcher textWatcher = new TextWatcher() {

        private List<SearchBean> searchList;

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            searchList = SearchBean.getList();
            String TextSearch = etSearch.getText().toString();
            if (TextSearch.length()==0){
                ivClear.setVisibility(View.GONE);
                searchList.clear();
            }else {
                ivClear.setVisibility(View.VISIBLE);
            }
            SearchAdapter adapter = new SearchAdapter(SearchActivity.this, searchList,R.layout.item_search);
            lvSearch.setAdapter(adapter);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

}
