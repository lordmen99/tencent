package com.mb.picvisionlive.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mb.picvisionlive.R;
import com.mb.picvisionlive.StartActivity;
import com.mb.picvisionlive.tools.SPUtils;

import java.util.List;

public class GridAdapter extends PagerAdapter {
	private boolean isFirstLogin = false;
	private List<View> views;
	private Activity activity;

	private static final String SHAREDPREFERENCES_NAME = "first_pref";

	public GridAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		((ViewPager) arg0).addView(views.get(arg1), 0);
		if (arg1 == views.size() - 1) {
			RelativeLayout mStartWeiboImageButton = (RelativeLayout) arg0
					.findViewById(R.id.grvid_05);
			ImageView iv_go = (ImageView) arg0.findViewById(R.id.iv_go);
			iv_go.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setGuided();
					activity.startActivity(new Intent(activity, StartActivity.class));
					activity.finish();
				} 
			}); 
			
//			mStartWeiboImageButton.setOnClickListener(new OnClickListener() {

//				@Override
//				public void onClick(View v) {
				
//					goHome();

//				}
//
//			});
		}
		return views.get(arg1);
	}

	

	/**
	 * 
	 */
	private void setGuided() {
		SPUtils.put(activity, "isFirstRun", true);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}
