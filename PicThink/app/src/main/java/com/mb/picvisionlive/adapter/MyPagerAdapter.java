package com.mb.picvisionlive.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**pagerview适配�?
 * @author Administrator
 *
 */
public class MyPagerAdapter extends PagerAdapter {

	private List<View> listViewLayoutCollections;
	
	public MyPagerAdapter(List<View> listViewLayoutCollections) {
		this.listViewLayoutCollections = listViewLayoutCollections;
	}
	
	@Override
	public int getCount() {
		return listViewLayoutCollections.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	//移除�?个布�?
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(listViewLayoutCollections.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {//将要显示的东东加入到ViewGroup�?
		((ViewPager)container).addView(listViewLayoutCollections.get(position), 0);
		return listViewLayoutCollections.get(position);
	}
	
	@Override
	public Parcelable saveState() {
		return null;
	}
}
