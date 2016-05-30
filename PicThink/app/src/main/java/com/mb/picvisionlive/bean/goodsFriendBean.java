package com.mb.picvisionlive.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class goodsFriendBean {
    private String icon;
    private String sex;
    private String nickname;
    private String message;
    private String friendid;

    @Override
    public String toString() {
        return "goodsFriendBean{" +
                "icon='" + icon + '\'' +
                ", sex='" + sex + '\'' +
                ", nickname='" + nickname + '\'' +
                ", message='" + message + '\'' +
                ", friendid='" + friendid + '\'' +
                '}';
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public static List<goodsFriendBean> getList(){
        List<goodsFriendBean> list = new ArrayList<goodsFriendBean>();
//        JSONObject obj = null;
        goodsFriendBean bean =  null;

        for(int i = 0,count = 5;i<count;i++){
            bean = new goodsFriendBean();
//            obj = array.optJSONObject(i);
            bean.setNickname("欧阳美美");
            bean.setMessage("为什么我看不到视频的直播呀");
            list.add(bean);
        }
        return list;
    }
}
