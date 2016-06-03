package com.mb.picvisionlive;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.mb.picvisionlive.adapter.HelpGridAdapter;
import com.mb.picvisionlive.adapter.HelpListAdapter;
import com.mb.picvisionlive.bean.QuestionBean;
import com.mb.picvisionlive.weight.ListViewForScrollView;
import com.mb.picvisionlive.weight.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {
    Context context = HelpActivity.this;
    HelpListAdapter mHelpListAdapter;
    HelpGridAdapter mHelpGridAdapter;

    List<QuestionBean> mHelpList = new ArrayList<QuestionBean>();
    List<QuestionBean> mHelpGrid = new ArrayList<QuestionBean>();

    String Questions_list[] = {"怎么认证?", "怎么充酱?", "怎么申诉?", "怎么上热门?"};
    String Questions_grid[] = {"充值问题", "直播问题", "账号问题", "申诉问题"};

    @Bind(R.id.help_listview)
    ListViewForScrollView helpListview;
    @Bind(R.id.help_gridview)
    MyGridView helpGridview;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {

        initHead("帮助与反馈");
        helpListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent =new Intent(context, HelpDetailActivity.class);
//                startActivity(intent);
            }
        });
        helpGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(context, QuestionListActivity.class);
                startActivity(intent);
            }
        });
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
        for (int i = 0; i < 4; i++) {
            QuestionBean bean = new QuestionBean();
            bean.setQuestion(Questions_grid[i]);
            mHelpGrid.add(bean);
        }
        mHelpListAdapter=new HelpListAdapter(context,mHelpList);
        mHelpGridAdapter=new HelpGridAdapter(context,mHelpGrid);
        helpListview.setAdapter(mHelpListAdapter);
        helpGridview.setAdapter(mHelpGridAdapter);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }



    @OnClick({ R.id.help_close_txt, R.id.help_suggest_txt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.help_close_txt:{
                Intent intent =new Intent(context, CloseAccountActivity.class);
                startActivity(intent);
                break;}
            case R.id.help_suggest_txt:{
                Intent intent =new Intent(context, SuggestCheckActivity.class);
                startActivity(intent);
                break;}
        }
    }
}
