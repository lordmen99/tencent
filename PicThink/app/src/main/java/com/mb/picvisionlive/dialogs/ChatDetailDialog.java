package com.mb.picvisionlive.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mb.picvisionlive.R;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class ChatDetailDialog extends Dialog implements View.OnClickListener {
    private Context context;
    public ChatDetailDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        wlp.height = 400;
        window.setAttributes(wlp);

        initView();
    }

    private void initView() {
        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("欧阳美美");
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                dismiss();
                break;
        }
    }
}
