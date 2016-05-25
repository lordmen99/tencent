package com.mb.picthinklive.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class AllGoodsBean {
    private String goodsname;
    private String imageurl;
    private String goodsprice;
    private String goodsid;

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(String goodsprice) {
        this.goodsprice = goodsprice;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public static List<AllGoodsBean> getList(){
        List<AllGoodsBean> list = new ArrayList<AllGoodsBean>();
//        JSONObject obj = null;
        AllGoodsBean bean =  null;

        for(int i = 0,count = 5;i<count;i++){
            bean = new AllGoodsBean();
//            obj = array.optJSONObject(i);
            bean.setGoodsname("Dior迪奥真我香水系列");
            bean.setGoodsprice("¥238.0");
            list.add(bean);
        }
        return list;
    }

    @Override
    public String toString() {
        return "AllGoodsBean{" +
                "goodsname='" + goodsname + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", goodsprice='" + goodsprice + '\'' +
                ", goodsid='" + goodsid + '\'' +
                '}';
    }
}
