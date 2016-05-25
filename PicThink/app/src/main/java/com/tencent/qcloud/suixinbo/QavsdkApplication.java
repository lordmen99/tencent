package com.tencent.qcloud.suixinbo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.qcloud.suixinbo.presenters.InitBusinessHelper;
import com.tencent.qcloud.suixinbo.utils.SxbLogImpl;

import java.util.LinkedList;
import java.util.List;


/**
 * 全局Application
 */
public class QavsdkApplication extends Application {

    private static QavsdkApplication app;
    private static Context context;
    private static List<Activity> activities;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).diskCacheSize(100 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove for release app
                .build();
//		 Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);


        context = getApplicationContext();
        activities = new LinkedList<>();

        SxbLogImpl.init(getApplicationContext());

        //初始化APP
        InitBusinessHelper.initApp(context);

        //创建AVSDK 控制器类
    }

    public static Context getContext() {
        return context;
    }

    public static QavsdkApplication getInstance(){
        return app;
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static Activity getTopActivity(){
        if (0 != activities.size()){
            return activities.get(activities.size()-1);
        }

        return null;
    }

    public static void exitApplication(){
        for (Activity activity : activities){
            activity.finish();
        }
    }


    /**
     * 异步加载图片统一配置项
     */
    private DisplayImageOptions options;

    /**
     *
     * @Description ImagerLoader显示图片
     */
    public DisplayImageOptions getOptions() {
        if (null == options) {
            options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
        }
        return options;
    }



    /**
     *
     * @Description ImagerLoader显示设置 圆形
     */
//    public DisplayImageOptions getOptionsCircle() {
//        return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.moren).showImageForEmptyUri(R.drawable.moren).displayer(new RoundedBitmapDisplayer(360)).showImageOnFail(R.drawable.moren).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();
//    }
//
//    public DisplayImageOptions getOptionsReac() {
//        return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.moren).showImageForEmptyUri(R.drawable.moren).showImageOnFail(R.drawable.moren).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).build();
//    }

}
