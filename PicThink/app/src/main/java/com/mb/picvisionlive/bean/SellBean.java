package com.mb.picvisionlive.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class SellBean {
    private String goodsId;
    private String goodsName;
    private String goodsPrice;
    private String goodsCount;
    private String goodsGain;
    private String goodsImage;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsGain() {
        return goodsGain;
    }

    public void setGoodsGain(String goodsGain) {
        this.goodsGain = goodsGain;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    @Override
    public String toString() {
        return "SellBean{" +
                "goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsCount='" + goodsCount + '\'' +
                ", goodsGain='" + goodsGain + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                '}';
    }
    public static List<SellBean> getList(){
        List<SellBean> list = new ArrayList<SellBean>();
//        JSONObject obj = null;
        SellBean bean =  null;

        for(int i = 0,count = 4;i<count;i++){
            bean = new SellBean();
//            obj = array.optJSONObject(i);
            bean.setGoodsName("美肤宝面膜贴女补水保湿控油收缩毛孔的哟");
            bean.setGoodsCount("32");
            bean.setGoodsGain("100");
            bean.setGoodsPrice("¥218");
            list.add(bean);
        }
        return list;
    }
}
