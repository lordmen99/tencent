package com.mb.picvisionlive;

import android.content.Context;
import android.widget.TextView;

import com.mb.picvisionlive.adapter.HelpListAdapter;
import com.mb.picvisionlive.bean.QuestionBean;
import com.mb.picvisionlive.weight.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QuestionListActivity extends BaseActivity {
    Context context = QuestionListActivity.this;
    HelpListAdapter mHelpListAdapter;
    List<QuestionBean> mHelpList = new ArrayList<QuestionBean>();
    String Questions_list[] = {"怎么认证?", "怎么充酱?", "怎么申诉?", "怎么上热门?"};


    @Bind(R.id.question_listview)
    ListViewForScrollView questionListview;

    @Bind(R.id.question_type_txt)
    TextView question_type_txt;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_question_list);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("帮助和反馈");
        question_type_txt.setText(getIntent().getStringExtra("title"));
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

        questionListview.setAdapter(mHelpListAdapter);

    }
    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


}
