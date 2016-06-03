package com.mb.picvisionlive.bean;

import java.util.List;

public class ProvinceModel {
	private String name;
	private List<CityModel> cityList;
	private String zipcode;

	public ProvinceModel() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityModel> cityList) {
		this.cityList = cityList;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public ProvinceModel(String name, List<CityModel> cityList, String zipcode) {
		super();
		this.name = name;
		this.cityList = cityList;
		this.zipcode = zipcode;
	}

}
