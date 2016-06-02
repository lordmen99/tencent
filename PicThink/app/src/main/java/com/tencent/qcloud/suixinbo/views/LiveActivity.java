package com.tencent.qcloud.suixinbo.views;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mb.picvisionlive.GoodsDetailActivity;
import com.mb.picvisionlive.R;
import com.mb.picvisionlive.adapter.AudienceIconAdapter;
import com.mb.picvisionlive.adapter.PopShopAdapter;
import com.mb.picvisionlive.bean.AllGoodsBean;
import com.mb.picvisionlive.bean.AudienceIconBean;
import com.mb.picvisionlive.bean.ShopBean;
import com.mb.picvisionlive.fragment.DialogFragmentWindow;
import com.mb.picvisionlive.weight.HorizontialListView;
import com.tencent.TIMUserProfile;
import com.tencent.av.sdk.AVView;
import com.tencent.qcloud.suixinbo.QavsdkApplication;
import com.tencent.qcloud.suixinbo.adapters.AllGoodsAdapter;
import com.tencent.qcloud.suixinbo.adapters.ChatMsgListAdapter;
import com.tencent.qcloud.suixinbo.adapters.SwipAdapter;
import com.tencent.qcloud.suixinbo.avcontrollers.QavsdkControl;
import com.tencent.qcloud.suixinbo.model.ChatEntity;
import com.tencent.qcloud.suixinbo.model.CurLiveInfo;
import com.tencent.qcloud.suixinbo.model.LiveInfoJson;
import com.tencent.qcloud.suixinbo.model.MySelfInfo;
import com.tencent.qcloud.suixinbo.presenters.EnterLiveHelper;
import com.tencent.qcloud.suixinbo.presenters.LiveHelper;
import com.tencent.qcloud.suixinbo.presenters.OKhttpHelper;
import com.tencent.qcloud.suixinbo.presenters.ProfileInfoHelper;
import com.tencent.qcloud.suixinbo.presenters.viewinface.EnterQuiteRoomView;
import com.tencent.qcloud.suixinbo.presenters.viewinface.LiveView;
import com.tencent.qcloud.suixinbo.presenters.viewinface.ProfileView;
import com.tencent.qcloud.suixinbo.utils.Constants;
import com.tencent.qcloud.suixinbo.utils.GlideCircleTransform;
import com.tencent.qcloud.suixinbo.utils.SxbLog;
import com.tencent.qcloud.suixinbo.utils.UIUtils;
import com.tencent.qcloud.suixinbo.views.customviews.HeartLayout;
import com.tencent.qcloud.suixinbo.views.customviews.InputTextMsgDialog;
import com.tencent.qcloud.suixinbo.views.customviews.MembersDialog;
import com.tencent.qcloud.suixinbo.views.customviews.MyListView;
import com.tencent.qcloud.suixinbo.views.customviews.SwipeListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Live直播类
 */
public class LiveActivity extends FragmentActivity implements EnterQuiteRoomView, LiveView, View.OnClickListener, ProfileView {
    private static final String TAG = LiveActivity.class.getSimpleName();
    private static final int GETPROFILE_JOIN = 0x200;

    private EnterLiveHelper mEnterRoomHelper;
    private ProfileInfoHelper mUserInfoHelper;
    private LiveHelper mLiveHelper;

    private ArrayList<ChatEntity> mArrayListChatEntity;
    private ChatMsgListAdapter mChatMsgListAdapter;
    private static final int MINFRESHINTERVAL = 500;
    private static final int UPDAT_WALL_TIME_TIMER_TASK = 1;
    private static final int ClOSE_IMSDK = 2;
    private static final int TIMEOUT_INVITE = 3;
    private boolean mBoolRefreshLock = false;
    private boolean mBoolNeedRefresh = false;
    private final Timer mTimer = new Timer();
    private ArrayList<ChatEntity> mTmpChatList = new ArrayList<ChatEntity>();//缓冲队列
    private TimerTask mTimerTask = null;
    private static final int REFRESH_LISTVIEW = 5;
    private Dialog mMemberDg, closeCfmDg, inviteDg;
    private HeartLayout mHeartLayout;
    private TextView mLikeTv;
    private HeartBeatTask mHeartBeatTask;//心跳
    private ImageView mHeadIcon;
    private long mSecond = 0;
    private String formatTime;
    private Timer mHearBeatTimer;
    private ObjectAnimator mObjAnim;
    private int thumbUp = 0;
    private long admireTime = 0;
    private int watchCount = 0;


    private String backGroundId;

    private TextView tvMembers;

    private Dialog mDetailDialog;
    private HorizontialListView hlv_audience;
    private TextView member_count_bottom;
    private TextView member_count_right;
    private TextView tv_guanzhu;
    private TextView tv_lives;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 不锁屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_live);

        //进出房间的协助类
        mEnterRoomHelper = new EnterLiveHelper(this, this);
        //房间内的交互协助类
        mLiveHelper = new LiveHelper(this, this);
        // 用户资料类
        mUserInfoHelper = new ProfileInfoHelper(this);

        initView();

        initGuanZhuView();

        registerReceiver();
        backGroundId = CurLiveInfo.getHostID();
        //进入房间流程
        mEnterRoomHelper.startEnterRoom();

        //QavsdkControl.getInstance().setCameraPreviewChangeCallback();

        initHoriLitView();

        QavsdkApplication.getInstance().addActivity(this);
    }

    private void initGuanZhuView() {

        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {//是主播
            tv_guanzhu.setVisibility(View.GONE);
            member_count_bottom.setVisibility(View.GONE);
            member_count_right.setVisibility(View.VISIBLE);
            tv_lives.setPadding(0, 10, 0, 0);
//            Drawable rightDrawable = getResources().getDrawable(R.mipmap.live_user_female);
//            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
//            tv_lives.setCompoundDrawables(null, null, rightDrawable, null);
        }else {
            hlv_audience.setPadding(90,0,0,0);
        }
    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case UPDAT_WALL_TIME_TIMER_TASK:
                    updateWallTime();
                    break;
                case REFRESH_LISTVIEW:
                    doRefreshListView();
                    break;
                case ClOSE_IMSDK:
                    mLiveHelper.perpareQuitRoom(false);
                    mEnterRoomHelper.quiteLive();
                    break;
                case TIMEOUT_INVITE:
                    String id = "" + msg.obj;
                    cancelInviteView(id);
                    mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_HOST_CANCELINVITE, id);
                    break;
            }
            return false;
        }
    });

    /**
     * 时间格式化
     */
    private void updateWallTime() {
        String hs, ms, ss;

        long h, m, s;
        h = mSecond / 3600;
        m = (mSecond % 3600) / 60;
        s = (mSecond % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }

        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }

        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
        if (hs.equals("00")) {
            formatTime = ms + ":" + ss;
        } else {
            formatTime = hs + ":" + ms + ":" + ss;
        }

        if (Constants.HOST == MySelfInfo.getInstance().getIdStatus()  ) {
            SxbLog.i(TAG, " refresh time ");
        }
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //AvSurfaceView 初始化成功
            if (action.equals(Constants.ACTION_SURFACE_CREATED)) {
                //打开摄像头
                if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
                    mLiveHelper.openCameraAndMic();
                }

            }

            if (action.equals(Constants.ACTION_CAMERA_OPEN_IN_LIVE)) {//有人打开摄像头
                ArrayList<String> ids = intent.getStringArrayListExtra("ids");
                //如果是自己本地直接渲染
                for (String id : ids) {
                    if (id.equals(MySelfInfo.getInstance().getId())) {
                        showVideoView(true, id);
                        return;
//                        ids.remove(id);
                    }
                }
                //其他人一并获取
                int requestCount = CurLiveInfo.getCurrentRequestCount();
                mLiveHelper.RequestViewList(ids);
                requestCount = requestCount + ids.size();
                CurLiveInfo.setCurrentRequestCount(requestCount);
//                }
            }

            if (action.equals(Constants.ACTION_SWITCH_VIDEO)) {//点击成员回调
                backGroundId = intent.getStringExtra(Constants.EXTRA_IDENTIFIER);

                if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {//自己是主播
                    if (backGroundId.equals(MySelfInfo.getInstance().getId())) {//背景是自己
                        mHostCtrView.setVisibility(View.VISIBLE);
                        mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                    } else {//背景是其他成员
                        mHostCtrView.setVisibility(View.INVISIBLE);
                        mVideoMemberCtrlView.setVisibility(View.VISIBLE);
                    }
                } else {//自己成员方式
                    if (backGroundId.equals(MySelfInfo.getInstance().getId())) {//背景是自己
                        mVideoMemberCtrlView.setVisibility(View.VISIBLE);
                        mNomalMemberCtrView.setVisibility(View.INVISIBLE);
                    } else if (backGroundId.equals(CurLiveInfo.getHostID())) {//主播自己
                        mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                        mNomalMemberCtrView.setVisibility(View.VISIBLE);
                    } else {
                        mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
                        mNomalMemberCtrView.setVisibility(View.INVISIBLE);
                    }

                }

            }
            if (action.equals(Constants.ACTION_HOST_LEAVE)) {//主播结束
                quiteLivePassively();
            }


        }
    };

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_SURFACE_CREATED);
        intentFilter.addAction(Constants.ACTION_HOST_ENTER);
        intentFilter.addAction(Constants.ACTION_CAMERA_OPEN_IN_LIVE);
        intentFilter.addAction(Constants.ACTION_SWITCH_VIDEO);
        intentFilter.addAction(Constants.ACTION_HOST_LEAVE);
        registerReceiver(mBroadcastReceiver, intentFilter);

    }

    private void unregisterReceiver() {
        unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * 初始化UI
     */
    private View avView;
    private TextView BtnBack, BtnInput, Btnflash, BtnSwitch, BtnBeauty, BtnMic, BtnHeart, BtnNormal, mVideoChat, BtnCtrlVideo, BtnCtrlMic, BtnHungup, mBeautyConfirm;
    private TextView inviteView1, inviteView2, inviteView3;
    private ListView mListViewMsgItems;
    private LinearLayout mHostCtrView, mNomalMemberCtrView, mVideoMemberCtrlView, mBeautySettings;
    private FrameLayout mFullControllerUi, mBackgound;
    private SeekBar mBeautyBar;
    private int mBeautyRate;

    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else {
            SxbLog.d(TAG, "load icon: " + avatar);
            RequestManager req = Glide.with(this);
            req.load(avatar).transform(new GlideCircleTransform(this)).into(view);
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mHostCtrView = (LinearLayout) findViewById(R.id.host_bottom_layout);
        mNomalMemberCtrView = (LinearLayout) findViewById(R.id.member_bottom_layout);
        mVideoMemberCtrlView = (LinearLayout) findViewById(R.id.video_member_bottom_layout);
        mVideoChat = (TextView) findViewById(R.id.video_interact);
        mHeartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        mHeadIcon = (ImageView) findViewById(R.id.head_icon);
        mVideoMemberCtrlView.setVisibility(View.INVISIBLE);

        tvMembers = (TextView) findViewById(R.id.member_counts);

        BtnCtrlVideo = (TextView) findViewById(R.id.camera_controll);
        BtnCtrlMic = (TextView) findViewById(R.id.mic_controll);
        BtnHungup = (TextView) findViewById(R.id.close_member_video);

        TextView tv_message = (TextView) findViewById(R.id.tv_message);//消息图标
        TextView tv_sale = (TextView) findViewById(R.id.tv_sale);//出售商品
        TextView tv_vrprop = (TextView) findViewById(R.id.tv_vrprop);//VR道具
        TextView tv_lianxian = (TextView) findViewById(R.id.tv_lianxian);//连线
        TextView tv_friends = (TextView) findViewById(R.id.tv_friends);//好友聊天
        TextView tv_share = (TextView) findViewById(R.id.tv_share);//分享
        TextView tv_sing = (TextView) findViewById(R.id.tv_sing);//唱歌
        TextView tv_camera = (TextView) findViewById(R.id.tv_camera);//翻转
        TextView tv_close = (TextView) findViewById(R.id.tv_close);//关闭
        RelativeLayout rl_user = (RelativeLayout) findViewById(R.id.rl_user);//主播信息
        hlv_audience = (HorizontialListView) findViewById(R.id.hlv_audience);//观众头像
        member_count_bottom = (TextView) findViewById(R.id.member_count_bottom);//观看数
        member_count_right = (TextView) findViewById(R.id.member_counts);//观看数2
        tv_guanzhu = (TextView) findViewById(R.id.tv_guanzhu);//关注按钮
        tv_lives = (TextView) findViewById(R.id.tv_lives);
        TextView tv_shopping = (TextView) findViewById(R.id.tv_shopping);//购物

        tv_guanzhu.setOnClickListener(this);
        tv_shopping.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        tv_sale.setOnClickListener(this);
        tv_vrprop.setOnClickListener(this);
        tv_lianxian.setOnClickListener(this);
        tv_friends.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        tv_sing.setOnClickListener(this);
        tv_camera.setOnClickListener(this);
        tv_close.setOnClickListener(this);
        rl_user.setOnClickListener(this);


        BtnCtrlVideo.setOnClickListener(this);
        BtnCtrlMic.setOnClickListener(this);
        BtnHungup.setOnClickListener(this);
        TextView roomId = (TextView) findViewById(R.id.room_id);
        roomId.setText(CurLiveInfo.getChatRoomId());

        //for 测试用
        TextView paramVideo = (TextView) findViewById(R.id.param_video);
        paramVideo.setOnClickListener(this);
        tvTipsMsg = (TextView) findViewById(R.id.qav_tips_msg);
        tvTipsMsg.setTextColor(Color.GREEN);
        paramTimer.schedule(task, 1000, 1000);


        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            mHostCtrView.setVisibility(View.VISIBLE);
            mNomalMemberCtrView.setVisibility(View.GONE);
//            Btnflash = (TextView) findViewById(R.id.flash_btn);
//            BtnSwitch = (TextView) findViewById(R.id.switch_cam);
//            BtnBeauty = (TextView) findViewById(R.id.beauty_btn);
//            BtnMic = (TextView) findViewById(R.id.mic_btn);
//            mVideoChat.setVisibility(View.VISIBLE);
//            Btnflash.setOnClickListener(this);
//            BtnSwitch.setOnClickListener(this);
//            BtnBeauty.setOnClickListener(this);
//            BtnMic.setOnClickListener(this);
//            mVideoChat.setOnClickListener(this);
            inviteView1 = (TextView) findViewById(R.id.invite_view1);
            inviteView2 = (TextView) findViewById(R.id.invite_view2);
            inviteView3 = (TextView) findViewById(R.id.invite_view3);
            inviteView1.setOnClickListener(this);
            inviteView2.setOnClickListener(this);
            inviteView3.setOnClickListener(this);

            initBackDialog();
            initDetailDailog();
            mMemberDg = new MembersDialog(this, R.style.floag_dialog, this);
            showHeadIcon(mHeadIcon, MySelfInfo.getInstance().getAvatar());
            mBeautySettings = (LinearLayout) findViewById(R.id.qav_beauty_setting);
            mBeautyConfirm = (TextView) findViewById(R.id.qav_beauty_setting_finish);
            mBeautyConfirm.setOnClickListener(this);
            mBeautyBar = (SeekBar) (findViewById(R.id.qav_beauty_progress));
            mBeautyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                    SxbLog.d("SeekBar", "onStopTrackingTouch");
                    Toast.makeText(LiveActivity.this, "beauty " + mBeautyRate + "%", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                    SxbLog.d("SeekBar", "onStartTrackingTouch");
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    // TODO Auto-generated method stub
                    mBeautyRate = progress;
                    QavsdkControl.getInstance().getAVContext().getVideoCtrl().inputBeautyParam(getBeautyProgress(progress));

                }
            });
        } else {
            initInviteDialog();
            mNomalMemberCtrView.setVisibility(View.VISIBLE);
            mHostCtrView.setVisibility(View.GONE);
            BtnInput = (TextView) findViewById(R.id.message_input);
            BtnInput.setOnClickListener(this);
            mLikeTv = (TextView) findViewById(R.id.member_send_good);
            mLikeTv.setOnClickListener(this);
            mVideoChat.setVisibility(View.GONE);


            List<String> ids = new ArrayList<>();
            ids.add(CurLiveInfo.getHostID());
            showHeadIcon(mHeadIcon, CurLiveInfo.getHostAvator());




        }
        BtnNormal = (TextView) findViewById(R.id.normal_btn);
        BtnNormal.setOnClickListener(this);
        mFullControllerUi = (FrameLayout) findViewById(R.id.controll_ui);
        avView = findViewById(R.id.av_video_layer_ui);//surfaceView;
        BtnBack = (TextView) findViewById(R.id.btn_back);
        BtnBack.setOnClickListener(this);

        mListViewMsgItems = (ListView) findViewById(R.id.im_msg_listview);
        mArrayListChatEntity = new ArrayList<ChatEntity>();
        mChatMsgListAdapter = new ChatMsgListAdapter(this, mListViewMsgItems, mArrayListChatEntity);
        mListViewMsgItems.setAdapter(mChatMsgListAdapter);

        tvMembers.setText("" + CurLiveInfo.getMembers());

    }


    @Override
    protected void onResume() {
        super.onResume();
        QavsdkControl.getInstance().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        QavsdkControl.getInstance().onPause();
    }


    /**
     * 直播心跳
     */
    private class HeartBeatTask extends TimerTask {
        @Override
        public void run() {
            String host = CurLiveInfo.getHostID();
            SxbLog.i(TAG, "HeartBeatTask " + host);
            OKhttpHelper.getInstance().sendHeartBeat(host, CurLiveInfo.getMembers(), CurLiveInfo.getAdmires(), 0);
        }
    }

    /**
     * 记时器
     */
    private class VideoTimerTask extends TimerTask {
        public void run() {
            SxbLog.i(TAG, "timeTask ");
            ++mSecond;
            if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST)
                mHandler.sendEmptyMessage(UPDAT_WALL_TIME_TIMER_TASK);
        }
    }

    @Override
    protected void onDestroy() {
        watchCount = 0;
        super.onDestroy();
        if (null != mHearBeatTimer) {
            mHearBeatTimer.cancel();
            mHearBeatTimer = null;
        }

        if (null != paramTimer) {
            paramTimer.cancel();
            paramTimer = null;
        }
        inviteViewCount = 0;
        thumbUp = 0;
        CurLiveInfo.setMembers(0);
        CurLiveInfo.setAdmires(0);
        CurLiveInfo.setCurrentRequestCount(0);
        unregisterReceiver();
        QavsdkControl.getInstance().clearVideoMembers();
        QavsdkControl.getInstance().onDestroy();
        QavsdkApplication.getInstance().removeActivity(this);
    }


    /**
     * 点击Back键
     */
    @Override
    public void onBackPressed() {
        quiteLiveByPurpose();

    }

    /**
     * 主动退出直播
     */
    private void quiteLiveByPurpose() {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            if (backDialog.isShowing() == false)
                backDialog.show();

        } else {
            mLiveHelper.perpareQuitRoom(true);
            mEnterRoomHelper.quiteLive();
        }
    }


    private Dialog backDialog;
    private Dialog userDialog;

    private void initBackDialog() {
        backDialog = new Dialog(this, R.style.BackDialog);
        backDialog.setContentView(R.layout.dialog_end_live);

        TextView tvSure = (TextView) backDialog.findViewById(R.id.btn_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果是直播，发消息
                mLiveHelper.perpareQuitRoom(true);
                backDialog.dismiss();
            }
        });
        TextView tvCancel = (TextView) backDialog.findViewById(R.id.btn_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backDialog.cancel();
            }
        });
//        backDialog.show();
    }

    /**
     * e
     * 被动退出直播
     */
    private void quiteLivePassively() {
        mLiveHelper.perpareQuitRoom(false);
        mEnterRoomHelper.quiteLive();
    }

    @Override
    public void readyToQuit() {
        mEnterRoomHelper.quiteLive();
    }

    /**
     * 完成进出房间流程
     *
     * @param id_status
     * @param isSucc
     */
    @Override
    public void EnterRoomComplete(int id_status, boolean isSucc) {
        Toast.makeText(LiveActivity.this, "EnterRoom  " + id_status + " isSucc " + isSucc, Toast.LENGTH_SHORT).show();
        //必须得进入房间之后才能初始化UI
        mEnterRoomHelper.initAvUILayer(avView);

        //修正摄像头镜像
        mLiveHelper.fixCamera();
        if (isSucc == true) {
            //IM初始化
            mLiveHelper.initTIMListener("" + CurLiveInfo.getRoomNum());

            if (id_status == Constants.HOST) {//主播方式加入房间成功
                //开启摄像头渲染画面
                SxbLog.i(TAG, "createlive EnterRoomComplete isSucc" + isSucc);
            } else {
                //发消息通知上线
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_EnterLive, "");
            }
        }
    }


    @Override
    public void QuiteRoomComplete(int id_status, boolean succ, LiveInfoJson liveinfo) {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            if ((getBaseContext() != null) && (null != mDetailDialog) && (mDetailDialog.isShowing() == false)) {
                mDetailTime.setText(formatTime);
                mDetailAdmires.setText("" + CurLiveInfo.getAdmires());
                mDetailWatchCount.setText("" + watchCount);
                mDetailDialog.show();
            }
        } else {
            finish();
        }

    }


    private TextView mDetailTime, mDetailAdmires, mDetailWatchCount;

    private void initDetailDailog() {
        mDetailDialog = new Dialog(this, R.style.dialog);
        mDetailDialog.setContentView(R.layout.dialog_live_detail);
        mDetailTime = (TextView) mDetailDialog.findViewById(R.id.tv_time);
        mDetailAdmires = (TextView) mDetailDialog.findViewById(R.id.tv_admires);
        mDetailWatchCount = (TextView) mDetailDialog.findViewById(R.id.tv_members);

        mDetailDialog.setCancelable(false);

        TextView tvCancel = (TextView) mDetailDialog.findViewById(R.id.btn_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
                finish();
            }
        });
//        mDetailDialog.show();
    }

    /**
     * 成员状态变更
     *
     * @param id
     * @param name
     */
    @Override
    public void memberJoin(String id, String name) {
        watchCount++;
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "join live", Constants.MEMBER_ENTER);

        CurLiveInfo.setMembers(CurLiveInfo.getMembers() + 1);
        tvMembers.setText("" + CurLiveInfo.getMembers());
    }

    @Override
    public void memberQuit(String id, String name) {
        refreshTextListView(TextUtils.isEmpty(name) ? id : name, "quite live", Constants.MEMBER_EXIT);

        if (CurLiveInfo.getMembers() > 1) {
            CurLiveInfo.setMembers(CurLiveInfo.getMembers() - 1);
            tvMembers.setText("" + CurLiveInfo.getMembers());
        }

        //如果存在视频互动，取消
        QavsdkControl.getInstance().closeMemberView(id);
    }

    /**
     * 有成员退群
     *
     * @param list 成员ID 列表
     */
    @Override
    public void memberQuiteLive(String[] list) {
    }

    /**
     * 有成员入群
     *
     * @param list 成员ID 列表
     */
    @Override
    public void memberJoinLive(final String[] list) {
    }

    @Override
    public void alreadyInLive(String[] list) {
        for (String id : list) {
            if (id.equals(MySelfInfo.getInstance().getId())) {
                QavsdkControl.getInstance().setSelfId(MySelfInfo.getInstance().getId());
                QavsdkControl.getInstance().setLocalHasVideo(true, MySelfInfo.getInstance().getId());
            } else {
                QavsdkControl.getInstance().setRemoteHasVideo(true, id, AVView.VIDEO_SRC_TYPE_CAMERA);
            }
        }

    }



    private static int index = 0;

    /**
     * 加载视频数据
     *
     * @param isLocal 是否是本地数据
     * @param id      身份
     */
    @Override
    public void showVideoView(boolean isLocal, String id) {
        SxbLog.i(TAG, "showVideoView " + id);

        //渲染本地Camera
        if (isLocal == true) {
            SxbLog.i(TAG, "showVideoView host :" + MySelfInfo.getInstance().getId());
            QavsdkControl.getInstance().setSelfId(MySelfInfo.getInstance().getId());
            QavsdkControl.getInstance().setLocalHasVideo(true, MySelfInfo.getInstance().getId());
            mLiveHelper.fixCamera();
            //主播通知用户服务器
            if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
                mEnterRoomHelper.notifyServerCreateRoom();

                //主播心跳
                mHearBeatTimer = new Timer(true);
                mHeartBeatTask = new HeartBeatTask();
                mHearBeatTimer.schedule(mHeartBeatTask, 1000, 3 * 1000);


            }
        } else {
//            QavsdkControl.getInstance().addRemoteVideoMembers(id);
            QavsdkControl.getInstance().setRemoteHasVideo(true, id, AVView.VIDEO_SRC_TYPE_CAMERA);
        }

    }


    private float getBeautyProgress(int progress) {
        SxbLog.d("shixu", "progress: " + progress);
        return (9.0f * progress / 100.0f);
    }


    @Override
    public void showInviteDialog() {
        if ((inviteDg != null) && (getBaseContext() != null) && (inviteDg.isShowing() != true)) {
            inviteDg.show();
        }
    }

    @Override
    public void hideInviteDialog() {
        if ((inviteDg != null) && (inviteDg.isShowing() == true)) {
            inviteDg.dismiss();
        }
    }


    @Override
    public void refreshText(String text, String name) {
        if (text != null) {
            refreshTextListView(name, text, Constants.TEXT_TYPE);
        }
    }

    @Override
    public void refreshThumbUp() {
        CurLiveInfo.setAdmires(CurLiveInfo.getAdmires() + 1);
        mHeartLayout.addFavor();
    }

    @Override
    public void refreshUI(String id) {
        //当主播选中这个人，而他主动退出时需要恢复到正常状态
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST)
            if (!backGroundId.equals(CurLiveInfo.getHostID()) && backGroundId.equals(id)) {
                backToNormalCtrlView();
            }
    }


    private int inviteViewCount = 0;

    @Override
    public boolean showInviteView(String id) {
        int index = QavsdkControl.getInstance().getAvailableViewIndex(1);
        if (index == -1) {
            Toast.makeText(LiveActivity.this, "the invitation's upper limit is 3", Toast.LENGTH_SHORT).show();
            return false;
        }
        int requetCount = index + inviteViewCount;
        if (requetCount > 3) {
            Toast.makeText(LiveActivity.this, "the invitation's upper limit is 3", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (hasInvited(id)) {
            Toast.makeText(LiveActivity.this, "it has already invited", Toast.LENGTH_SHORT).show();
            return false;
        }
        switch (requetCount) {
            case 1:
                inviteView1.setText(id);
                inviteView1.setVisibility(View.VISIBLE);
                inviteView1.setTag(id);

                break;
            case 2:
                inviteView2.setText(id);
                inviteView2.setVisibility(View.VISIBLE);
                inviteView2.setTag(id);
                break;
            case 3:
                inviteView3.setText(id);
                inviteView3.setVisibility(View.VISIBLE);
                inviteView3.setTag(id);
                break;
        }
        mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MUlTI_HOST_INVITE, "", id);
        inviteViewCount++;
        //30s超时取消
        Message msg = new Message();
        msg.what = TIMEOUT_INVITE;
        msg.obj = id;
        mHandler.sendMessageDelayed(msg, 30 * 1000);
        return true;
    }


    /**
     * 判断是否邀请过同一个人
     *
     * @param id
     * @return
     */
    private boolean hasInvited(String id) {
        if (id.equals(inviteView1.getTag())) {
            return true;
        }
        if (id.equals(inviteView2.getTag())) {
            return true;
        }
        if (id.equals(inviteView3.getTag())) {
            return true;
        }
        return false;
    }

    @Override
    public void cancelInviteView(String id) {
        if ((inviteView1 != null) && (inviteView1.getTag() != null)) {
            if (inviteView1.getTag().equals(id)) {
            }
            if (inviteView1.getVisibility() == View.VISIBLE) {
                inviteView1.setVisibility(View.INVISIBLE);
                inviteView1.setTag("");
                inviteViewCount--;
            }
        }

        if (inviteView2 != null && inviteView2.getTag() != null) {
            if (inviteView2.getTag().equals(id)) {
                if (inviteView2.getVisibility() == View.VISIBLE) {
                    inviteView2.setVisibility(View.INVISIBLE);
                    inviteView2.setTag("");
                    inviteViewCount--;
                }
            } else {
                Log.i(TAG, "cancelInviteView inviteView2 is null");
            }
        } else {
            Log.i(TAG, "cancelInviteView inviteView2 is null");
        }

        if (inviteView3 != null && inviteView3.getTag() != null) {
            if (inviteView3.getTag().equals(id)) {
                if (inviteView3.getVisibility() == View.VISIBLE) {
                    inviteView3.setVisibility(View.INVISIBLE);
                    inviteView3.setTag("");
                    inviteViewCount--;
                }
            } else {
                Log.i(TAG, "cancelInviteView inviteView3 is null");
            }
        } else {
            Log.i(TAG, "cancelInviteView inviteView3 is null");
        }


    }

    @Override
    public void cancelMemberView(String id) {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
        } else {
            mLiveHelper.closeCameraAndMic();//是自己成员关闭
        }
        mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, id);
        QavsdkControl.getInstance().closeMemberView(id);
        backToNormalCtrlView();
    }


    private void showReportDialog() {
        final Dialog reportDialog = new Dialog(this, R.style.report_dlg);
        reportDialog.setContentView(R.layout.dialog_live_report);

        TextView tvReportDirty = (TextView) reportDialog.findViewById(R.id.btn_dirty);
        TextView tvReportFalse = (TextView) reportDialog.findViewById(R.id.btn_false);
        TextView tvReportVirus = (TextView) reportDialog.findViewById(R.id.btn_virus);
        TextView tvReportIllegal = (TextView) reportDialog.findViewById(R.id.btn_illegal);
        TextView tvReportYellow = (TextView) reportDialog.findViewById(R.id.btn_yellow);
        TextView tvReportCancel = (TextView) reportDialog.findViewById(R.id.btn_cancel);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    default:
                        reportDialog.cancel();
                        break;
                }
            }
        };

        tvReportDirty.setOnClickListener(listener);
        tvReportFalse.setOnClickListener(listener);
        tvReportVirus.setOnClickListener(listener);
        tvReportIllegal.setOnClickListener(listener);
        tvReportYellow.setOnClickListener(listener);
        tvReportCancel.setOnClickListener(listener);

        reportDialog.setCanceledOnTouchOutside(true);
        reportDialog.show();
    }

    private void showHostDetail() {
        Dialog hostDlg = new Dialog(this, R.style.host_info_dlg);
        hostDlg.setContentView(R.layout.host_info_layout);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window dlgwin = hostDlg.getWindow();
        WindowManager.LayoutParams lp = dlgwin.getAttributes();
        dlgwin.setGravity(Gravity.TOP);
        lp.width = (int) (display.getWidth()); //设置宽度

        hostDlg.getWindow().setAttributes(lp);
        hostDlg.show();

        TextView tvHost = (TextView) hostDlg.findViewById(R.id.tv_host_name);
        tvHost.setText(CurLiveInfo.getHostName());
        ImageView ivHostIcon = (ImageView) hostDlg.findViewById(R.id.iv_host_icon);
        showHeadIcon(ivHostIcon, CurLiveInfo.getHostAvator());
        TextView tvLbs = (TextView) hostDlg.findViewById(R.id.tv_host_lbs);
        tvLbs.setText(UIUtils.getLimitString(CurLiveInfo.getAddress(), 6));
        ImageView ivReport = (ImageView) hostDlg.findViewById(R.id.iv_report);
        ivReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReportDialog();
            }
        });
    }

    private boolean checkInterval() {
        if (0 == admireTime) {
            admireTime = System.currentTimeMillis();
            return true;
        }
        long newTime = System.currentTimeMillis();
        if (newTime >= admireTime + 1000) {
            admireTime = newTime;
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_back:
                quiteLiveByPurpose();
                break;
            case R.id.message_input:
                inputMsgDialog();
                break;
            case R.id.member_send_good:
                // 添加飘星动画
                mHeartLayout.addFavor();
                if (checkInterval()) {
                    mLiveHelper.sendC2CMessage(Constants.AVIMCMD_Praise, "", CurLiveInfo.getHostID());
                    CurLiveInfo.setAdmires(CurLiveInfo.getAdmires() + 1);
                } else {
                    //Toast.makeText(this, getString(R.string.text_live_admire_limit), Toast.LENGTH_SHORT).show();
                }
                break;
//            case R.id.flash_btn:
//                if (mLiveHelper.isFrontCamera() == true) {
//                    Toast.makeText(LiveActivity.this, "this is front cam", Toast.LENGTH_SHORT).show();
//                } else {
//                    mLiveHelper.toggleFlashLight();
//                }
//                break;
//            case R.id.switch_cam:
//                mLiveHelper.switchCamera();
//                break;
//            case R.id.mic_btn:
//                if (mLiveHelper.isMicOpen() == true) {
//                    BtnMic.setBackgroundResource(R.drawable.icon_mic_close);
//                    mLiveHelper.muteMic();
//                } else {
//                    BtnMic.setBackgroundResource(R.drawable.icon_mic_open);
//                    mLiveHelper.openMic();
//                }
//                break;
//            case R.id.head_up_layout:
//                showHostDetail();
//                break;
//            case R.id.fullscreen_btn:
//                mFullControllerUi.setVisibility(View.INVISIBLE);
//                BtnNormal.setVisibility(View.VISIBLE);
//                break;
//            case R.id.av_screen_layout:
//                mHostCtrView.setVisibility(View.VISIBLE);
//                mVideoMemberCtrlView.setVisibility(View.INVISIBLE);
//                mBackgound.setVisibility(View.GONE);
//                break;
            case R.id.normal_btn:
                mFullControllerUi.setVisibility(View.VISIBLE);
                BtnNormal.setVisibility(View.GONE);
                break;
            case R.id.video_interact:
                mMemberDg.setCanceledOnTouchOutside(true);
                mMemberDg.show();
                break;
            case R.id.camera_controll:
                Toast.makeText(LiveActivity.this, "切换" + backGroundId + "camrea 状态", Toast.LENGTH_SHORT).show();
                if (backGroundId.equals(MySelfInfo.getInstance().getId())) {//自己关闭自己
                    mLiveHelper.toggleCamera();
                } else {
                    mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MULTI_HOST_CONTROLL_CAMERA, backGroundId, backGroundId);//主播关闭自己
                }
                break;
            case R.id.mic_controll:
                Toast.makeText(LiveActivity.this, "切换" + backGroundId + "mic 状态", Toast.LENGTH_SHORT).show();
                if (backGroundId.equals(MySelfInfo.getInstance().getId())) {//自己关闭自己
                    mLiveHelper.toggleMic();
                } else {
                    mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MULTI_HOST_CONTROLL_MIC, backGroundId, backGroundId);//主播关闭自己
                }
                break;
            case R.id.close_member_video://主动关闭成员摄像头
                cancelMemberView(backGroundId);
                break;
//            case R.id.beauty_btn:
//                if (mBeautySettings != null) {
//                    if (mBeautySettings.getVisibility() == View.GONE) {
//                        mBeautySettings.setVisibility(View.VISIBLE);
//                        mFullControllerUi.setVisibility(View.INVISIBLE);
//                    } else {
//                        mBeautySettings.setVisibility(View.GONE);
//                        mFullControllerUi.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    SxbLog.i(TAG, "beauty_btn mTopBar  is null ");
//                }
//                break;
            case R.id.qav_beauty_setting_finish:
                mBeautySettings.setVisibility(View.GONE);
                mFullControllerUi.setVisibility(View.VISIBLE);
                break;
            case R.id.invite_view1:
                inviteView1.setVisibility(View.INVISIBLE);
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView1.getTag());
                break;
            case R.id.invite_view2:
                inviteView2.setVisibility(View.INVISIBLE);
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView2.getTag());
                break;
            case R.id.invite_view3:
                inviteView3.setVisibility(View.INVISIBLE);
                mLiveHelper.sendGroupMessage(Constants.AVIMCMD_MULTI_CANCEL_INTERACT, "" + inviteView3.getTag());
                break;
            case R.id.param_video:
                showTips = !showTips;
                break;
            case  R.id.tv_message://消息
                inputMsgDialog();//消息输入框
                break;
            case  R.id.tv_sale://出售商品
                SalePopWindow();//商品的Dialog
                break;
            case  R.id.tv_vrprop://VR道具
               Toast.makeText(this,"暂无开通，敬请期待。",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.tv_lianxian://连线
               showLianXianPopWindow(view);
                break;
            case  R.id.tv_friends://好友聊天
                DialogFragmentWindow friendsDialog = new DialogFragmentWindow();
                friendsDialog.setStyle(DialogFragment.STYLE_NO_TITLE,R.style.CustomDatePickerDialog);
                friendsDialog.show(getSupportFragmentManager(), "");
                break;
            case  R.id.tv_share://分享
                showSharePopWindow(view);
                break;
            case  R.id.tv_sing://唱歌
                Toast.makeText(this,"暂无开通，敬请期待。",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.tv_camera://翻转，闪光灯
                showCameraPopWindow(view);
                break;
            case  R.id.tv_close://关闭直播
                quiteLiveByPurpose();
                break;
            case  R.id.rl_user://主播信息

               showUserDialog();
                break;
            case R.id.tv_guanzhu://关注按钮
                tv_guanzhu.setVisibility(View.GONE);
                member_count_bottom.setVisibility(View.GONE);
                member_count_right.setVisibility(View.VISIBLE);
                tv_lives.setPadding(0, 10, 0, 0);
                hlv_audience.setPadding(-30,0,0,0);
                break;
            case R.id.tv_shopping://购物
                ShopPopWindow();
                break;
        }
    }


    //获取观众头像
    private void initHoriLitView() {
        List<AudienceIconBean> AudienceIconList = AudienceIconBean.getList();
        AudienceIconAdapter adapter = new AudienceIconAdapter(this,AudienceIconList,R.layout.item_audience_icon);
        hlv_audience.setAdapter(adapter);
        }


/*******************************购物的PopWindow*********************************************/
    private void ShopPopWindow() {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_shop_window, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, 500);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        TextView tv_close = (TextView) view.findViewById(R.id.tv_close);
        TextView tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        //关闭
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
                //立即购买
                tv_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater inflater = getLayoutInflater();
                        View buyView = inflater.inflate(R.layout.pop_shop_confirm_window, null);
                        final PopupWindow popupWindow = new PopupWindow(buyView, LinearLayout.LayoutParams.MATCH_PARENT, 500);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setFocusable(true);
                        popupWindow.showAtLocation(buyView, Gravity.BOTTOM, 0, 0);
                        TextView tv_close = (TextView) buyView.findViewById(R.id.tv_close);
                        TextView tv_pay = (TextView) buyView.findViewById(R.id.tv_pay);
                        tv_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }
                        });
                        //立即支付
                        tv_pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog  payDialog = new Dialog(LiveActivity.this, R.style.BackDialog);
                                payDialog.setContentView(R.layout.dialog_pay_live);
                                payDialog.show();
                                ImageView iv_close = (ImageView) payDialog.findViewById(R.id.iv_close);
                                TextView tv_confirm = (TextView) findViewById(R.id.tv_confirm);//确定
                                final CheckBox cb_sela = (CheckBox) payDialog.findViewById(R.id.cb_sela);//色拉余额
                                final CheckBox cb_weixin = (CheckBox) payDialog.findViewById(R.id.cb_weixin);//微信
                                final CheckBox cb_zhifubao = (CheckBox) payDialog.findViewById(R.id.cb_zhifubao);//支付宝
                                cb_sela.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (cb_sela.isChecked()){
                                            cb_weixin.setChecked(false);
                                            cb_zhifubao.setChecked(false);
                                        }
                                    }
                                });
                                cb_weixin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (cb_weixin.isChecked()){
                                            cb_sela.setChecked(false);
                                            cb_zhifubao.setChecked(false);
                                        }
                                    }
                                });
                                cb_zhifubao.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (cb_zhifubao.isChecked()){
                                            cb_weixin.setChecked(false);
                                            cb_sela.setChecked(false);
                                        }
                                    }
                                });
                                //确定
                                tv_confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        payDialog.dismiss();
                                    }
                                });
                                //关闭
                                iv_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        payDialog.dismiss();
                                    }
                                });
                            }
                        });
                    }
                });

        ListView lv_shop = (ListView) view.findViewById(R.id.lv_shop);
        List<ShopBean> shopBeanList = ShopBean.getList();
        PopShopAdapter shopAdapter = new PopShopAdapter(this,shopBeanList,R.layout.item_pop_shop);
        lv_shop.setAdapter(shopAdapter);
        lv_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //商品详情
                Intent intent = new Intent();
                intent.setClass(LiveActivity.this, GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
        //显示(在该控件下面)
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);


    }
/****************************************************************************************/




/********************************发消息弹出输入框*******************************************/

    private void inputMsgDialog() {
        InputTextMsgDialog inputMsgDialog = new InputTextMsgDialog(this, R.style.inputdialog, mLiveHelper, this);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = inputMsgDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        inputMsgDialog.getWindow().setAttributes(lp);
//        inputMsgDialog.setCancelable(true);
        inputMsgDialog.show();
    }
/******************************************************************************************/



/********************************关于购物商品模块*******************************************/
    /**
     * 测试商品
     */
    private SwipeListView mListView;
    private ArrayList<String> mList = new ArrayList<String>();
    private void initData() {
        for (int i = 0; i < 5; i++) {
            mList.add("Dior迪奥真我香水系列");
        }
    }
    /**
     * 出售商品列表
     */
    private void SalePopWindow() {

        initData();

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_sale_window, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, 700);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        //显示(在该控件下面)
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        //解决ListView抢占焦点
        ScrollView scroll = (ScrollView) view.findViewById(R.id.scroll);
        scroll.smoothScrollTo(0, 20);

        SwipeListView mListView = (SwipeListView) view.findViewById(R.id.lv_goods);
        MyListView lv_all_goods = (MyListView) view.findViewById(R.id.lv_all_goods);
        //所有商品
        List<AllGoodsBean> allGoodsList = AllGoodsBean.getList();
        AllGoodsAdapter alladapter = new AllGoodsAdapter(this,allGoodsList,R.layout.sale_window_item);
        lv_all_goods.setAdapter(alladapter);

        //代理的商品
        ListViewAdapter adapter = new ListViewAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //商品详情

                Intent intent = new Intent();
                intent.setClass(LiveActivity.this, GoodsDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    class ListViewAdapter extends SwipAdapter {

        public ListViewAdapter(Context context) {
            super(context);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }

        /**
         * B版本记得优化下ListView
         */
        protected View generateLeftView(final int position) {
             View  view=LayoutInflater.from(mContext).inflate(R.layout.sale_window_item,null);
            TextView tv_goodsname = (TextView) view.findViewById(R.id.tv_goodsname);
            tv_goodsname.setText(mList.get(position));

            return view;
        }
        //删除按钮
        protected View generateRightView(final int position) {
            TextView view = new TextView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 150);
            view.setLayoutParams(lp);
            view.setText("删除");
            view.setTextColor(Color.parseColor("#FFFFFF"));
            view.setGravity(Gravity.CENTER);
            view.setBackgroundColor(Color.parseColor("#FF6B6B"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "del " + mList.get(position), Toast.LENGTH_SHORT).show();
                    mList.remove(position);
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }
/******************************************************************************************/



/**************************************连线功能*******************************************/
    private void showLianXianPopWindow(View v) {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_lianxian_window, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();
        int popupHeight = view.getMeasuredHeight();
        int[] location = new int[2];
        //显示(在该控件上面)
        v.getLocationOnScreen(location);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);

        TextView tv_yuyin = (TextView) view.findViewById(R.id.tv_yuyin);
        TextView tv_shipin = (TextView) view.findViewById(R.id.tv_shipin);
        //点击语音连线
        tv_yuyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //点击视频连线
        tv_shipin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

/******************************************************************************************/



/**************************************分享*******************************************/
    private void showSharePopWindow(View v) {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_share_window, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

    }

/******************************************************************************************/




/**************************************连线功能*******************************************/
    private void showCameraPopWindow(View v) {
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_lianxian_window, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = view.getMeasuredWidth();
        int popupHeight = view.getMeasuredHeight();
        int[] location = new int[2];
        //显示(在该控件上面)
        v.getLocationOnScreen(location);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);

        TextView tv_shanguang = (TextView) view.findViewById(R.id.tv_yuyin);
        tv_shanguang.setText("闪光灯");
        TextView tv_xuanzhuan = (TextView) view.findViewById(R.id.tv_shipin);
        tv_xuanzhuan.setText("旋转");


        tv_shanguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        tv_xuanzhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
/******************************************************************************************/




/**************************************查看主播信息*******************************************/
private void showUserDialog() {

    userDialog = new Dialog(this, R.style.BackDialog);
    userDialog.setContentView(R.layout.dialog_user_live);
    userDialog.show();
    if (MySelfInfo.getInstance().getIdStatus() != Constants.HOST) {//不是主播
        RelativeLayout rl_audience = (RelativeLayout) userDialog.findViewById(R.id.rl_audience);
        TextView tv_jubao = (TextView) userDialog.findViewById(R.id.tv_jubao);
        rl_audience.setVisibility(View.VISIBLE);
        tv_jubao.setVisibility(View.VISIBLE);
    }
    ImageView iv_back_dialog = (ImageView) userDialog.findViewById(R.id.iv_back_dialog);
    iv_back_dialog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            userDialog.dismiss();
        }
    });

}
/******************************************************************************************/

     //for 测试获取测试参数
    private boolean showTips = false;
    private TextView tvTipsMsg;
    Timer paramTimer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (showTips) {
                        if (tvTipsMsg != null) {
                            String strTips = QavsdkControl.getInstance().getQualityTips();
                            strTips = praseString(strTips);
                            if (!TextUtils.isEmpty(strTips)) {
                                tvTipsMsg.setText(strTips);
                            }
                        }
                    } else {
                        tvTipsMsg.setText("");
                    }
                }
            });
        }
    };

    //for 测试 解析参数
    private String praseString(String video) {
        if (video.length() == 0) {
            return "";
        }
        String result = "";
        String splitItems[];
        String tokens[];
        splitItems = video.split("\\n");
        for (int i = 0; i < splitItems.length; ++i) {
            if (splitItems[i].length() < 2)
                continue;

            tokens = splitItems[i].split(":");
            if (tokens[0].length() == "mainVideoSendSmallViewQua".length()) {
                continue;
            }
            if (tokens[0].endsWith("BigViewQua")) {
                tokens[0] = "mainVideoSendViewQua";
            }
            if (tokens[0].endsWith("BigViewQos")) {
                tokens[0] = "mainVideoSendViewQos";
            }
            result += tokens[0] + ":\n" + "\t\t";
            for (int j = 1; j < tokens.length; ++j)
                result += tokens[j];
            result += "\n\n";
            //Log.d(TAG, "test:" + result);
        }
        //Log.d(TAG, "test:" + result);
        return result;
    }


    private void backToNormalCtrlView() {
        if (MySelfInfo.getInstance().getIdStatus() == Constants.HOST) {
            backGroundId = CurLiveInfo.getHostID();
            mHostCtrView.setVisibility(View.VISIBLE);
            mVideoMemberCtrlView.setVisibility(View.GONE);
        } else {
            backGroundId = CurLiveInfo.getHostID();
            mNomalMemberCtrView.setVisibility(View.VISIBLE);
            mVideoMemberCtrlView.setVisibility(View.GONE);
        }
    }





    /**
     * 主播邀请应答框
     */
    private void initInviteDialog() {
        inviteDg = new Dialog(this, R.style.dialog);
        inviteDg.setContentView(R.layout.invite_dialog);
        TextView hostId = (TextView) inviteDg.findViewById(R.id.host_id);
        hostId.setText(CurLiveInfo.getHostID());
        TextView agreeBtn = (TextView) inviteDg.findViewById(R.id.invite_agree);
        TextView refusebtn = (TextView) inviteDg.findViewById(R.id.invite_refuse);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mVideoMemberCtrlView.setVisibility(View.VISIBLE);
//                mNomalMemberCtrView.setVisibility(View.INVISIBLE);
                backGroundId = MySelfInfo.getInstance().getId();
                mLiveHelper.openCameraAndMic();
                mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MUlTI_JOIN, "", CurLiveInfo.getHostID());
                inviteDg.dismiss();
            }
        });

        refusebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLiveHelper.sendC2CMessage(Constants.AVIMCMD_MUlTI_REFUSE, "", CurLiveInfo.getHostID());
                inviteDg.dismiss();
            }
        });

        Window dialogWindow = inviteDg.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);
    }


    /**
     * 消息刷新显示
     *
     * @param name    发送者
     * @param context 内容
     * @param type    类型 （上线线消息和 聊天消息）
     */
    public void refreshTextListView(String name, String context, int type) {
        ChatEntity entity = new ChatEntity();
        entity.setSenderName(name);
        entity.setContext(context);
        entity.setType(type);
        //mArrayListChatEntity.add(entity);
        notifyRefreshListView(entity);
        //mChatMsgListAdapter.notifyDataSetChanged();

        mListViewMsgItems.setVisibility(View.VISIBLE);
        SxbLog.d(TAG, "refreshTextListView height " + mListViewMsgItems.getHeight());

        if (mListViewMsgItems.getCount() > 1) {
            if (true)
                mListViewMsgItems.setSelection(0);
            else
                mListViewMsgItems.setSelection(mListViewMsgItems.getCount() - 1);
        }
    }


    /**
     * 通知刷新消息ListView
     */
    private void notifyRefreshListView(ChatEntity entity) {
        mBoolNeedRefresh = true;
        mTmpChatList.add(entity);
        if (mBoolRefreshLock) {
            return;
        } else {
            doRefreshListView();
        }
    }


    /**
     * 刷新ListView并重置状态
     */
    private void doRefreshListView() {
        if (mBoolNeedRefresh) {
            mBoolRefreshLock = true;
            mBoolNeedRefresh = false;
            mArrayListChatEntity.addAll(mTmpChatList);
            mTmpChatList.clear();
            mChatMsgListAdapter.notifyDataSetChanged();

            if (null != mTimerTask) {
                mTimerTask.cancel();
            }
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    SxbLog.v(TAG, "doRefreshListView->task enter with need:" + mBoolNeedRefresh);
                    mHandler.sendEmptyMessage(REFRESH_LISTVIEW);
                }
            };
            //mTimer.cancel();
            mTimer.schedule(mTimerTask, MINFRESHINTERVAL);
        } else {
            mBoolRefreshLock = false;
        }
    }

    @Override
    public void updateProfileInfo(TIMUserProfile profile) {

    }

    @Override
    public void updateUserInfo(int requestCode, List<TIMUserProfile> profiles) {
        if (null != profiles) {
            switch (requestCode) {
                case GETPROFILE_JOIN:
                    for (TIMUserProfile user : profiles) {
                        tvMembers.setText("" + CurLiveInfo.getMembers());
                        SxbLog.w(TAG, "get nick name:" + user.getNickName());
                        SxbLog.w(TAG, "get remark name:" + user.getRemark());
                        SxbLog.w(TAG, "get avatar:" + user.getFaceUrl());
                        if (!TextUtils.isEmpty(user.getNickName())) {
                            refreshTextListView(user.getNickName(), "join live", Constants.MEMBER_ENTER);
                        } else {
                            refreshTextListView(user.getIdentifier(), "join live", Constants.MEMBER_ENTER);
                        }
                    }
                    break;
            }

        }
    }
}
