package com.mb.picvisionlive.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yangdm
 * Email:yangdm@bluemobi.cn
 * Description:(类的用途)
 */
public class SearchBean {
     private String searchIcon;
    private String searchName;
    private String searchSex;
//    private String search;


    public String getSearchIcon() {
        return searchIcon;
    }

    public void setSearchIcon(String searchIcon) {
        this.searchIcon = searchIcon;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchSex() {
        return searchSex;
    }

    public void setSearchSex(String searchSex) {
        this.searchSex = searchSex;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "searchIcon='" + searchIcon + '\'' +
                ", searchName='" + searchName + '\'' +
                ", searchSex='" + searchSex + '\'' +
                '}';
    }
    public static List<SearchBean> getList(){
        List<SearchBean> list = new ArrayList<SearchBean>();
//        JSONObject obj = null;
        SearchBean bean =  null;

        for(int i = 0,count = 5;i<count;i++){
            bean = new SearchBean();
//            obj = array.optJSONObject(i);
            bean.setSearchName("欧阳美美");

            list.add(bean);
        }
        return list;
    }
}
