package com.mb.picvisionlive;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.mb.picvisionlive.tools.SPUtils;


/**
 * Author: yangdm@bluemobi.cn
 * description:启动页
 * History
 */
public class StartAppActivity extends BaseActivity {
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
//				redirectTo();
				isFirst();
				break;

			default:
				break;
			}
		}
	};
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.act_start);
////		AppManager.getAppManager().addActivity(this);
//
//

//
//	}

	@Override
	public void setContentView() {
		setContentView(R.layout.act_start);
				mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(0);
			}
		}, 2000);
	}

	@Override
	public void findViewByid() {

	}

	@Override
	public void bodymethod() {

	}

	//	private void redirectTo() {
//		String versionName = Tools.getVerName(this);
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//		boolean isFirstRun = !prefs.getString("versionName", "").equals(versionName);
//		if (isFirstRun == false) {
//			Intent intent = new Intent(this, MainActivity.class);
//			startActivity(intent);
//		} else {
//			Editor editor = prefs.edit();
//			editor.putString("versionName", versionName);
//			editor.commit(); 
//			startActivity(new Intent(this, MainActivity.class));
//		}
//		finish();
//	}
	private void isFirst() {
		boolean isFirstRun = (boolean) SPUtils.get(this, "isFirstRun", false);
		
		if (isFirstRun == false) {
			Intent intent=null ;
					intent = new Intent(this, GuideActivity.class);
					startActivity(intent);
						finish();
		} else {

			Intent 	intent = new Intent(this, StartActivity.class);
			startActivity(intent);
				finish();
		}
	}
	
}