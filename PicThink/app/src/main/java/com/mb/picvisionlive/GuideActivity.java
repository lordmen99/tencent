package com.mb.picvisionlive;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mb.picvisionlive.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements OnPageChangeListener {
	private ViewPager vp;
	private GridAdapter adapter;
	private List<View> views;
	ImageView gridww2;
	private ImageView[] dots;

	private int currentIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_guide);
		//mQueue = Volley.newRequestQueue(this);
		gridww2 = (ImageView) findViewById(R.id.gridww2);
		initViews();
		initDots();
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.fragment_gruida, null));
		views.add(inflater.inflate(R.layout.fragment_gruidb, null));
		views.add(inflater.inflate(R.layout.fragment_gruidc, null));
		views.add(inflater.inflate(R.layout.fragment_gruidd, null));
		adapter = new GridAdapter(views, this);
		vp = (ViewPager) findViewById(R.id.viewpager);
		vp.setAdapter(adapter);
		vp.setOnPageChangeListener(this);
		
	}
	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		ll.getChildAt(1).setEnabled(true);
		dots = new ImageView[views.size()];

		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);
		}
 
		currentIndex = 0;
		dots[currentIndex].setEnabled(false);
	}

	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1
				|| currentIndex == position) {
			return;
		}

		dots[position].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = position;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		setCurrentDot(arg0);
	}

}
