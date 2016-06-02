package com.mb.picvisionlive.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class ShopBean {
    private String goodsName;
    private String goodsId;
    private String goodsContent;
    private String goodsImage;
    private String goodsPrice;
    private String goodsCount;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsContent() {
        return goodsContent;
    }

    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    @Override
    public String toString() {
        return "ShopBean{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsContent='" + goodsContent + '\'' +
                ", goodsImage='" + goodsImage + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsCount='" + goodsCount + '\'' +
                '}';
    }
    public static List<ShopBean> getList(){
        List<ShopBean> list = new ArrayList<ShopBean>();
//        JSONObject obj = null;
        ShopBean bean =  null;

        for(int i = 0,count = 5;i<count;i++){
            bean = new ShopBean();
//            obj = array.optJSONObject(i);
            bean.setGoodsName("面膜");
            bean.setGoodsContent("美肤宝面膜贴女补水保湿控油收缩毛孔的哟");
            bean.setGoodsCount("1");
            bean.setGoodsPrice("¥218");
            list.add(bean);
        }
        return list;
    }

}
