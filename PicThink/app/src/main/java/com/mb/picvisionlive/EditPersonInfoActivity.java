package com.mb.picvisionlive;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mb.picvisionlive.setting.PicConstants;
import com.mb.picvisionlive.tools.BitmapUtils;
import com.mb.picvisionlive.weight.CircularImage;
import com.tencent.qcloud.suixinbo.utils.Constants;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPersonInfoActivity extends BaseActivity {

    Context context = EditPersonInfoActivity.this;
    @Bind(R.id.edit_person_info_nick_text)
    TextView mNickText;
    @Bind(R.id.edit_person_info_sex_text)
    TextView mSexText;
    @Bind(R.id.edit_person_info_sign_text)
    TextView mSignText;
    @Bind(R.id.edit_person_info_job_text)
    TextView mJobText;
    @Bind(R.id.edit_person_info_head_img)
    CircularImage mheadImg;

    private boolean bPermission = false;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int NONE = 0;
    public static final int PHOTOHRAPH = 1;// 拍照
    public static final int PHOTOZOOM = 2; // 缩放
    public static final int PHOTORESOULT = 3;// 结果


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_person_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        bPermission = checkCropPermission();
    }

    @Override
    public void findViewByid() {
        initHead("编辑资料");
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.commom_right_txt, R.id.edit_person_info_head_line, R.id.edit_person_info_nick_line, R.id.edit_person_info_ID_line, R.id.edit_person_info_sex_line, R.id.edit_person_info_sign_line, R.id.edit_person_info_check_line, R.id.edit_person_info_age_line, R.id.edit_person_info_home_line, R.id.edit_person_info_job_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commom_right_txt:{
                break;}
            case R.id.edit_person_info_head_line:{
                showPhotoDialog();
                break;}
            case R.id.edit_person_info_nick_line:{
                Intent intent =new Intent(context, EditNickActivity.class);
                startActivity(intent);
                break;}
            case R.id.edit_person_info_ID_line:{
                break;}
            case R.id.edit_person_info_sex_line:{
                Intent intent =new Intent(context, EditSexActivity.class);
                startActivity(intent);
                break;}
            case R.id.edit_person_info_sign_line:{
                Intent intent =new Intent(context, EditSignActivity.class);
                startActivity(intent);
                break;}
            case R.id.edit_person_info_check_line:{
                break;}
            case R.id.edit_person_info_age_line:{
                break;}
            case R.id.edit_person_info_home_line:{
                break;}
            case R.id.edit_person_info_job_line:{
                Intent intent =new Intent(context, EditJobActivity.class);
                startActivity(intent);
                break;}
        }
    }

    private boolean checkCropPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(EditPersonInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(EditPersonInfoActivity.this, Manifest.permission.READ_PHONE_STATE)){
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0){
                ActivityCompat.requestPermissions(EditPersonInfoActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        Constants.WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }

        return true;
    }


    private  void startZoomCamera(int arg1){


                if(0 == arg1) {// 选择本地相片
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            IMAGE_UNSPECIFIED);
                    startActivityForResult(intent, PHOTOZOOM);
                }

                if(1 ==arg1) {// 拍照
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                            Environment.getExternalStorageDirectory(), "temp.jpg")));
                    System.out.println("=============" + Environment.getExternalStorageDirectory());
                    startActivityForResult(intent, PHOTOHRAPH);
                }



    }


    /**
     * 图片选择对话框
     */
    private void showPhotoDialog() {
        final Dialog pickDialog = new Dialog(this, R.style.floag_dialog);
        pickDialog.setContentView(R.layout.pic_choose);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window dlgwin = pickDialog.getWindow();
        WindowManager.LayoutParams lp = dlgwin.getAttributes();
        dlgwin.setGravity(Gravity.BOTTOM);
        lp.width = (int)(display.getWidth()); //设置宽度

        pickDialog.getWindow().setAttributes(lp);

        TextView camera = (TextView) pickDialog.findViewById(R.id.chos_camera);
        TextView picLib = (TextView) pickDialog.findViewById(R.id.pic_lib);
        TextView cancel = (TextView) pickDialog.findViewById(R.id.btn_cancel);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startZoomCamera(1);
                pickDialog.dismiss();
            }
        });

        picLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startZoomCamera(0);
                pickDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDialog.dismiss();
            }
        });

        pickDialog.show();
    }
    @Subscriber(tag = PicConstants.NICK_TAG)
    private void updateUserNickName(String nickname) {
        mNickText.setText(nickname);
    }
    @Subscriber(tag = PicConstants.SEX_TAG)
    private void updateUserWithSex(String sex) {
        mSexText.setText(sex);
    }
    @Subscriber(tag = PicConstants.SIGN_TAG)
    private void updateUserWithSign(String sign) {
        mSignText.setText(sign);
    }
    @Subscriber(tag = PicConstants.JOB_TAG)
    private void updateUserWithJob(String job) {
        mJobText.setText(job);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 64);
        intent.putExtra("outputY", 64);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTORESOULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTOHRAPH) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            System.out.println("------------------------" + picture.getPath());
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PHOTOZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTORESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
                // 100)压缩文件
                mheadImg.setImageBitmap(photo);

                getFile(photo);
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getFile(Bitmap photo) {
        boolean isSave = BitmapUtils.saveBitmapTofile(photo,"/zc_portraints.JPEG");

//        if(isSave) {
//            File file = BitmapUtils.getFileFromLocal(Environment.getExternalStorageDirectory().getAbsolutePath()+"/zc_portraints.JPEG");
//            List<File> list = new ArrayList<File>();
//            list.add(file);
//
//            //TODO  在这里写上传用户的头像
//            PhotosUploadedTotheServer(list);
//        }
    }


    @Override
    protected void onDestroy() {
        // 不要忘记注销！！！！
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
