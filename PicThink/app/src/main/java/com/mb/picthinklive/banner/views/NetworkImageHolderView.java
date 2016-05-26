package com.mb.picthinklive.banner.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<String>{
    private ImageView imageView;
//   private  TextView views;
//    private int len;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return imageView;
    }
//        public  void setTextView(TextView view,int len){
//            this.views = view;
//            this.len = len;
//        }
    @Override
    public void UpdateUI(Context context, final int position, String data) {
        ImageLoader.getInstance().displayImage(data,imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件
            }
        });
    }

}
