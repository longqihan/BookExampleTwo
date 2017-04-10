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
import android.widget.RelativeLayout;

public class MainActivity1 extends SlidingFragmentActivity implements
		OnClickListener {

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub

		initTabIndicator();
		super.onRestart();
	}

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();
	private RelativeLayout Allgrade, makeupexam, testarragement, Schedule,
			presion, finalexam;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity1_main);

		presion = (RelativeLayout) findViewById(R.id.s_ico);
		Schedule = (RelativeLayout) findViewById(R.id.schedule);
		Allgrade = (RelativeLayout) findViewById(R.id.all_Grade);
		finalexam = (RelativeLayout) findViewById(R.id.final_exam);
		makeupexam = (RelativeLayout) findViewById(R.id.makeup_test);
		testarragement = (RelativeLayout) findViewById(R.id.test_arragement);

		presion.setOnClickListener(this);
		Schedule.setOnClickListener(this);
		Allgrade.setOnClickListener(this);
		finalexam.setOnClickListener(this);
		makeupexam.setOnClickListener(this);
		testarragement.setOnClickListener(this);

		initRightMenu();
		initTabIndicator();

	}

	@SuppressLint("Recycle")
	public void onClick(View v) {

		resetOtherTabs();
		// FragmentManager fg = getSupportFragmentManager();
		// FragmentTransaction ft = fg.beginTransaction();

		switch (v.getId()) {
		case R.id.id_indicator_one:
			resetOtherTabs();
			mTabIndicator.get(0).setIconAlpha(1.0f);
			Intent intent = new Intent();
			intent.setClass(MainActivity1.this, MainActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		case R.id.id_indicator_two:
			resetOtherTabs();
			mTabIndicator.get(1).setIconAlpha(1.0f);

			break;
		case R.id.id_indicator_three:
			resetOtherTabs();
			mTabIndicator.get(2).setIconAlpha(1.0f);
			Intent intent3 = new Intent();
			intent3.setClass(MainActivity1.this, MainActivity2.class);
			startActivity(intent3);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);

			break;
		case R.id.id_indicator_four:
			mTabIndicator.get(3).setIconAlpha(1.0f);
			Intent intent6 = new Intent();
			intent6.setClass(MainActivity1.this, MainActivity3.class);
			startActivity(intent6);
			finish();
			break;
		case R.id.s_ico:
			Intent intent4 = new Intent();
			intent4.setClass(MainActivity1.this, Presionactivity.class);
			startActivity(intent4);
			break;
		case R.id.schedule:
			Intent intent5 = new Intent();
			intent5.setClass(MainActivity1.this, ScheduleActivity.class);
			startActivity(intent5);
			break;
		case R.id.all_Grade:
			Intent intent7 = new Intent();
			intent7.setClass(MainActivity1.this, AllgradeActivity.class);
			startActivity(intent7);
			break;
		case R.id.final_exam:
			Intent intent8 = new Intent();
			intent8.setClass(MainActivity1.this, FinalGradeActivity.class);
			startActivity(intent8);
			break;
		case R.id.makeup_test:
			Intent intent9 = new Intent();
			intent9.setClass(MainActivity1.this, MakeupExamActivity.class);
			startActivity(intent9);
			break;
		case R.id.test_arragement:
			Intent intent10 = new Intent();
			intent10.setClass(MainActivity1.this, TestArragementActivity.class);
			startActivity(intent10);
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

		two.setIconAlpha(1.0f);

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
