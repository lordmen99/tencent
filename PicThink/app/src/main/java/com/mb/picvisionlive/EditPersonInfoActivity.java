package com.mb.picvisionlive;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mb.picvisionlive.bean.CityModel;
import com.mb.picvisionlive.bean.DistrictModel;
import com.mb.picvisionlive.bean.ProvinceModel;
import com.mb.picvisionlive.chooseaddress.WheelView;
import com.mb.picvisionlive.chooseaddress.adapter.ArrayWheelAdapter;
import com.mb.picvisionlive.chooseaddress.views.OnWheelChangedListener;
import com.mb.picvisionlive.choosedata.ScreenInfoEntity;
import com.mb.picvisionlive.choosedata.VeDate;
import com.mb.picvisionlive.choosedata.WheelMain;
import com.mb.picvisionlive.setting.PicConstants;
import com.mb.picvisionlive.tools.BitmapUtils;
import com.mb.picvisionlive.tools.XmlParserHandler;
import com.mb.picvisionlive.weight.CircularImage;
import com.tencent.qcloud.suixinbo.model.MySelfInfo;
import com.tencent.qcloud.suixinbo.utils.Constants;
import com.tencent.qcloud.suixinbo.utils.SxbLog;
import com.tencent.qcloud.suixinbo.utils.UIUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPersonInfoActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {

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
    @Bind(R.id.edit_person_info_home_txt)
    TextView editPersonInfoHomeTxt;
    @Bind(R.id.edit_person_info_age_txt)
    TextView editPersonInfoAgeTxt;

    private boolean bPermission = false;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int NONE = 0;
    private static final int CROP_CHOOSE = 10;
    private static final int CAPTURE_IMAGE_CAMERA = 100;
    private static final int IMAGE_STORE = 200;
    private Uri iconUrl, iconCrop;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edit_person_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        bPermission = checkCropPermission();
    }
    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }
    @Override
    public void findViewByid() {
        initHead("编辑资料");
    }

    @Override
    public void bodymethod() {

    }



    @OnClick({R.id.commom_right_txt, R.id.edit_person_info_head_line, R.id.edit_person_info_nick_line, R.id.edit_person_info_ID_line, R.id.edit_person_info_sex_line, R.id.edit_person_info_sign_line, R.id.edit_person_info_check_line, R.id.edit_person_info_age_line, R.id.edit_person_info_home_line, R.id.edit_person_info_job_line})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.commom_right_txt: {
                break;
            }
            case R.id.edit_person_info_head_line: {
                showPhotoDialog();
                break;
            }
            case R.id.edit_person_info_nick_line: {
                Intent intent = new Intent(context, EditNickActivity.class);
                intent.putExtra("nick",mNickText.getText().toString());
                startActivity(intent);
                break;
            }
            case R.id.edit_person_info_ID_line: {
                break;
            }
            case R.id.edit_person_info_sex_line: {
                Intent intent = new Intent(context, EditSexActivity.class);
                intent.putExtra("sex",mSexText.getText().toString());
                startActivity(intent);
                break;
            }
            case R.id.edit_person_info_sign_line: {
                Intent intent = new Intent(context, EditSignActivity.class);
                intent.putExtra("sign",mSignText.getText().toString());
                startActivity(intent);
                break;
            }
            case R.id.edit_person_info_check_line: {
                break;
            }
            case R.id.edit_person_info_age_line: {//年龄
                showAgeDialog();
                break;
            }
            case R.id.edit_person_info_home_line: {//家乡
                showSelectCity();
                break;
            }
            case R.id.tv_cancel:// 城市列表取消
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                break;
            case R.id.tv_confirm:// 城市列表确定
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                province = mProvinceDatas[mViewProvince.getCurrentItem()];
                String[] cities = mCitisDatasMap.get(province);
                city = cities[mViewCity.getCurrentItem()];
                String address = province + city;
                editPersonInfoHomeTxt.setText(address);

                break;
            case R.id.edit_person_info_job_line: {
                Intent intent = new Intent(context, EditJobActivity.class);
                intent.putExtra("job",mJobText.getText().toString());
                startActivity(intent);
                break;
            }
        }
    }


    private boolean checkCropPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(EditPersonInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(EditPersonInfoActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(EditPersonInfoActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        Constants.WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }

        return true;
    }



    private Uri createCoverUri(String type) {
        String filename = MySelfInfo.getInstance().getId()+ type + ".jpg";
        File outputImage = new File(Environment.getExternalStorageDirectory(), filename);
        if (ContextCompat.checkSelfPermission(EditPersonInfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(EditPersonInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_PERMISSION_REQ_CODE);
            return null;
        }
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(outputImage);
    }


    /**
     * 获取图片资源
     *
     * @param type
     */
    private void getPicFrom(int type) {
        if (!bPermission){
            Toast.makeText(this, getString(R.string.tip_no_permission), Toast.LENGTH_SHORT).show();
            return;
        }
        switch (type) {
            case CAPTURE_IMAGE_CAMERA:
                Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                iconUrl = createCoverUri("_icon");
                intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, iconUrl);
                startActivityForResult(intent_photo, CAPTURE_IMAGE_CAMERA);
                break;
            case IMAGE_STORE:
                iconUrl = createCoverUri("_select_icon");
                Intent intent_album = new Intent("android.intent.action.GET_CONTENT");
                intent_album.setType("image/*");
                startActivityForResult(intent_album, IMAGE_STORE);
                break;

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
                getPicFrom(CAPTURE_IMAGE_CAMERA);
                pickDialog.dismiss();
            }
        });

        picLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPicFrom(IMAGE_STORE);
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
        iconCrop = createCoverUri("_icon_crop");

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, iconCrop);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CROP_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
        case CAPTURE_IMAGE_CAMERA:
        startPhotoZoom(iconUrl);
        break;
        case IMAGE_STORE:
        String path = UIUtils.getPath(this, data.getData());
        if (null != path){
            SxbLog.e("startPhotoZoom", "startPhotoZoom->path:" + path);
            File file = new File(path);
            startPhotoZoom(Uri.fromFile(file));
        }
        break;
        case CROP_CHOOSE:
            Log.e("===iconCrop===",iconCrop.getPath());
        //mUploadHelper.uploadCover(iconCrop.getPath());
            Bitmap bitmap=decodeUriAsBitmap(iconCrop);
            mheadImg.setImageBitmap(bitmap);
        break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver()
                    .openInputStream(uri));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -// 100)压缩文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    private void getFile(Bitmap photo) {
        boolean isSave = BitmapUtils.saveBitmapTofile(photo, "/zc_portraints.JPEG");

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


/***************************************年龄选择*************************************************/
    /**
     * 申明日期组件
     */
    private AlertDialog selectCityalertDialog;
    WheelMain wheelMain;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String date;
    private String dateSave;

    private void showAgeDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View timePickerView = inflater.inflate(R.layout.v_time_picker, null);
        ScreenInfoEntity screenInfo = new ScreenInfoEntity(this);
        //为true显示小时分钟，false则不显示
        wheelMain = new WheelMain(timePickerView, false);
//       wheelMain.screenheight = screenInfo.getHeight();
        Calendar calendar = Calendar.getInstance();
        if (VeDate.isDate(date, "yyyy-MM-dd HH:mm")) {
            try {
                calendar.setTime(dateFormat.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        final int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);

        wheelMain.initDateTimePicker(year, month, day, hour, min);

        selectCityalertDialog = new AlertDialog.Builder(this).create();
        selectCityalertDialog.show();
        selectCityalertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectCityalertDialog.getWindow().setGravity(Gravity.BOTTOM);
        selectCityalertDialog.getWindow().setContentView(timePickerView);
        TextView tv_time_cancel = (TextView) selectCityalertDialog.getWindow().findViewById(R.id.tv_cancel);
        TextView tv_time_confirm = (TextView) selectCityalertDialog.getWindow().findViewById(R.id.tv_confirm);
         final TextView tv_xingzuo = (TextView) selectCityalertDialog.getWindow().findViewById(R.id.tv_xingzuo);//星座
         final TextView tv_year = (TextView) selectCityalertDialog.getWindow().findViewById(R.id.tv_year);//年龄

        tv_time_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSave = wheelMain.getTime();
                Date confirm = VeDate.parse(dateSave, "yyyy-MM-dd");
                String dateSave = VeDate.format(confirm, "yyyy年MM月dd日");
                 String xingzuo = tv_xingzuo.getText().toString();
                 String age = tv_year.getText().toString();
                editPersonInfoAgeTxt.setText(age+","+xingzuo);

                if (selectCityalertDialog != null && selectCityalertDialog.isShowing()) {
                    selectCityalertDialog.dismiss();
                }
            }
        });
        tv_time_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectCityalertDialog != null && selectCityalertDialog.isShowing()) {
                    selectCityalertDialog.dismiss();
                }
            }
        });


    }


/***************************************家乡选择**********************************************/
    /**
     * 地址选择所有成员
     */
    private AlertDialog alertDialog;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    protected String[] mProvinceDatas;
    protected String[] mProvinceZipCodeDatas;
    protected String mProviceCityCurrentZipCode = "";
    protected String mCityCurrentZipCode = "";
    protected String mCurrentDistrictName = "";
    protected String mCurrentZipCode = "";
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    private String district;
    private String city;
    private String province;
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
    protected Map<String, String> mProviceZipcodeDatasMap = new HashMap<String, String>();
    protected Map<String, String> mCityZipcodeDatasMap = new HashMap<String, String>();
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 城市选择对话框
     */
    private void showSelectCity() {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this).create();
        }
        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.getWindow().setContentView(R.layout.popup_city_home);

        mViewProvince = (WheelView) alertDialog.getWindow().findViewById(R.id.id_province);
        // mViewProvince.setWheelBackground(Color.WHITE);
        mViewCity = (WheelView) alertDialog.getWindow().findViewById(R.id.id_city);
        mViewDistrict = (WheelView) alertDialog.getWindow().findViewById(R.id.id_district);

        TextView tv_cancel = (TextView) alertDialog.getWindow().findViewById(R.id.tv_cancel);
        TextView tv_confirm = (TextView) alertDialog.getWindow().findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);

        setUpListener();
        setUpData();
    }

    /**
     * 添加change事件
     */
    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
        mViewDistrict.addChangingListener(this);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }

        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(EditPersonInfoActivity.this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(3);
        mViewCity.setVisibleItems(3);
        mViewDistrict.setVisibleItems(3);
        updateCities();
        updateAreas();
    }

    private void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            provinceList = handler.getDataList();
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                mProviceCityCurrentZipCode = provinceList.get(0).getZipcode();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    mCityCurrentZipCode = cityList.get(0).getZipcode();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }

            mProvinceDatas = new String[provinceList.size()];
            mProvinceZipCodeDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                mProvinceDatas[i] = provinceList.get(i).getName();
                mProvinceZipCodeDatas[i] = provinceList.get(i).getZipcode();
                mProviceZipcodeDatasMap.put(provinceList.get(i).getName(), provinceList.get(i).getZipcode());
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                String[] cityId = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    cityNames[j] = cityList.get(j).getName();
                    cityId[j] = cityList.get(j).getZipcode();
                    mCityZipcodeDatasMap.put(cityList.get(j).getName(), cityList.get(j).getZipcode());
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
            mProviceCityCurrentZipCode = mProviceZipcodeDatasMap.get(mCurrentProviceName);
        } else if (wheel == mViewCity) {
            updateAreas();
            mCityCurrentZipCode = mCityZipcodeDatasMap.get(mCurrentCityName);
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

}
