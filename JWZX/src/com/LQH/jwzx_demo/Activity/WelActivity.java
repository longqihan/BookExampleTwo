package com.LQH.jwzx_demo.Activity;

import com.LQH.jwzx_demo.utils.MyAppliction;
import com.zhy.zhy_slidemenu_demo02.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class WelActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1200; // 延迟秒
	private SharedPreferences preferences;
	private Editor editor;
	protected MyAppliction myAppliction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
		
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (preferences.getBoolean("firststart", true)) {
					editor = preferences.edit();
					// 将登录标志位设置为false，下次登录时不再显示引导页面
					editor.putBoolean("firststart", false);
					editor.commit();
					Intent intent = new Intent();
					intent.setClass(WelActivity.this,AndyViewPagerActivity.class);
					WelActivity.this.startActivity(intent);
					WelActivity.this.finish();
				} else {
					myAppliction = (MyAppliction) getApplication();
				    myAppliction.setLoginstate("12");
					Intent intent = new Intent();
					intent.setClass(WelActivity.this, LoginActivity.class);
					WelActivity.this.startActivity(intent);
					WelActivity.this.finish();

				}

			}
		}, SPLASH_DISPLAY_LENGHT);
	}

}
