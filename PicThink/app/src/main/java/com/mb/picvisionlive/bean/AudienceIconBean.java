package com.mb.picvisionlive.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class AudienceIconBean {
    private String ImageIcon;
    private String audienceId;

    public String getImageIcon() {
        return ImageIcon;
    }

    public void setImageIcon(String imageIcon) {
        ImageIcon = imageIcon;
    }

    public String getAudienceId() {
        return audienceId;
    }

    public void setAudienceId(String audienceId) {
        this.audienceId = audienceId;
    }

    @Override
    public String toString() {
        return "AudienceIconBean{" +
                "ImageIcon='" + ImageIcon + '\'' +
                ", audienceId='" + audienceId + '\'' +
                '}';
    }

    public static List<AudienceIconBean> getList(){
        List<AudienceIconBean> list = new ArrayList<AudienceIconBean>();
//        JSONObject obj = null;
        AudienceIconBean bean =  null;

        for(int i = 0,count = 10;i<count;i++){
            bean = new AudienceIconBean();
//            obj = array.optJSONObject(i);

            list.add(bean);
        }
        return list;
    }
}
