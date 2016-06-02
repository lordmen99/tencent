package com.mb.picvisionlive;

import android.content.Context;
import android.os.Bundle;

import com.mb.picvisionlive.adapter.HelpListAdapter;
import com.mb.picvisionlive.bean.QuestionBean;
import com.mb.picvisionlive.weight.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SuggestCheckActivity extends BaseActivity {
    Context context = SuggestCheckActivity.this;
    HelpListAdapter mHelpListAdapter;
    List<QuestionBean> mHelpList = new ArrayList<QuestionBean>();
    String Questions_list[] = {"怎么认证?", "怎么充酱?", "怎么申诉?", "怎么上热门?"};

    @Bind(R.id.suggest_check_listview)
    ListViewForScrollView mListview;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_suggest_check);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("意见反馈");
    }

    @Override
    public void bodymethod() {
        getData();
    }
    private void getData() {
        for (int i = 0; i < 4; i++) {
            QuestionBean bean = new QuestionBean();
            bean.setQuestion(Questions_list[i]);
            mHelpList.add(bean);
        }

        mHelpListAdapter=new HelpListAdapter(context,mHelpList);

        mListview.setAdapter(mHelpListAdapter);

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
