package com.mb.picthinklive.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.mb.picthinklive.R;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class ChatDetailDialog extends Dialog{

    public ChatDetailDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_detail_dialog);


    }
}
