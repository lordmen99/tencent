package com.mb.picvisionlive;

import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mb.picvisionlive.bean.CityModel;
import com.mb.picvisionlive.bean.DistrictModel;
import com.mb.picvisionlive.bean.ProvinceModel;
import com.mb.picvisionlive.chooseaddress.WheelView;
import com.mb.picvisionlive.chooseaddress.adapter.ArrayWheelAdapter;
import com.mb.picvisionlive.chooseaddress.views.OnWheelChangedListener;
import com.mb.picvisionlive.tools.XmlParserHandler;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ADDAddressActivity extends BaseActivity implements View.OnClickListener, OnWheelChangedListener {


    @Bind(R.id.et_consignee)
    EditText etConsignee;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.tv_province_city)
    TextView tvProvinceCity;
    @Bind(R.id.et_detail_address)
    EditText etDetailAddress;
    @Bind(R.id.cb_moren_address)
    CheckBox cbMorenAddress;
    @Bind(R.id.tv_add_confirm)
    TextView tvAddConfirm;

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
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
    }

    @Override
    public void findViewByid() {
        initHead("地址添加");
        tvProvinceCity.setOnClickListener(this);
        tvAddConfirm.setOnClickListener(this);
    }

    @Override
    public void bodymethod() {

    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_province_city://选择城市
                showSelectCity();
                break;
            case R.id.tv_add_confirm:
                finish();
                break;
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
                String[] districtis = mDistrictDatasMap.get(city);
                district = districtis[mViewDistrict.getCurrentItem()];
                String address = province+city+district;
                tvProvinceCity.setText(address);
                break;
        }
    }

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
        alertDialog.getWindow().setContentView(R.layout.popup_city);

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
            cities = new String[] { "" };
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
            areas = new String[] { "" };
        }

        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(ADDAddressActivity.this, mProvinceDatas));
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
