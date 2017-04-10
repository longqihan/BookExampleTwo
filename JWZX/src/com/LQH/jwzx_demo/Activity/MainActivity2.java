package com.LQH.jwzx_demo.Activity;

import java.util.ArrayList;
import java.util.List;

import com.LQH.jwzx_demo.Fragment.MLiftFragment;
import com.LQH.jwzx_demo.View.ChangeColorIconWithTextView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zhy.zhy_slidemenu_demo02.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class MainActivity2 extends SlidingFragmentActivity implements OnClickListener {

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity2_main);
		initRightMenu();
		initTabIndicator();

	}

	@SuppressLint("Recycle")
	public void onClick(View v) {
		

		resetOtherTabs();
		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			Intent intent = new Intent();
			intent.setClass(MainActivity2.this, MainActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		case R.id.id_indicator_two:
			resetOtherTabs();
			mTabIndicator.get(1).setIconAlpha(1.0f);
			Intent intent2 = new Intent();
			intent2.setClass(MainActivity2.this, MainActivity1.class);
			startActivity(intent2);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		case R.id.id_indicator_three:
			resetOtherTabs();
			mTabIndicator.get(2).setIconAlpha(1.0f);

			
			
			break;
		case R.id.id_indicator_four:
			resetOtherTabs();
			mTabIndicator.get(3).setIconAlpha(1.0f);
			Intent intent3 = new Intent();
			intent3.setClass(MainActivity2.this, MainActivity3.class);
			startActivity(intent3);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		default:
			break;
		}

	}

	private void initTabIndicator() {
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

		three.setIconAlpha(1.0f);

	}

	// 重置其他的Tab

	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}
	private void initRightMenu() {

		Fragment leftMenuFragment = new MLiftFragment();
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);

		// 设置滑动菜单视图的宽
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的宽
		menu.setFadeDegree(0.35f);

	}
	
}
