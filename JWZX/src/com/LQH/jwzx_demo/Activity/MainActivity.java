package com.LQH.jwzx_demo.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.LQH.jwzx_demo.Fragment.MLiftFragment;
import com.LQH.jwzx_demo.Fragment.MyFragment1;
import com.LQH.jwzx_demo.Fragment.MyFragment2;
import com.LQH.jwzx_demo.Fragment.MyFragment3;
import com.LQH.jwzx_demo.Fragment.MyFragment4;
import com.LQH.jwzx_demo.UiAdapter.MyFragmentAdapter;
import com.LQH.jwzx_demo.View.ChangeColorIconWithTextView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zhy.zhy_slidemenu_demo02.R;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class MainActivity extends SlidingFragmentActivity implements
		OnPageChangeListener, OnClickListener {

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

	private ViewPager mViewPaper, myViewPager;
	private TextView tv_tab0, tv_tab1, tv_tab2, tv_tab3; // 3个�?项卡
	private ImageView line_tab; // tab选项卡的下划�?
	private int moveOne = 0; // 下划线移动一个�?项卡
	private boolean isScrolling = false; // 手指是否在滑�?
	private boolean isBackScrolling = false; // 手指离开后的回弹
	private long startTime = 0;
	private long currentTime = 0;
	private List<ImageView> images;
	private List<View> dots;
	private int currentItem;
	// 记录上一次点的位�?
	private int oldPosition = 0;
	// 存放图片的id
	private int[] imageIds = new int[] { R.drawable.top1, R.drawable.top2,
			R.drawable.top3, R.drawable.top4, R.drawable.top3 };
	private ViewPagerAdapter adapter;
	private ScheduledExecutorService scheduledExecutorService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initViews();
		initRightMenu();
		initLineImage();
		initTabIndicator();
	}

	private void initViews() {
		mViewPaper = (ViewPager) findViewById(R.id.vp);

		// 显示的图�?
		images = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);
			images.add(imageView);
		}
		// 显示的小�?
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.dot_0));
		dots.add(findViewById(R.id.dot_1));
		dots.add(findViewById(R.id.dot_2));
		dots.add(findViewById(R.id.dot_3));
		dots.add(findViewById(R.id.dot_4));

		adapter = new ViewPagerAdapter();
		mViewPaper.setAdapter(adapter);

		mViewPaper
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// title.setText(titles[position]);
						dots.get(position).setBackgroundResource(
								R.drawable.dot_focused);
						dots.get(oldPosition).setBackgroundResource(
								R.drawable.dot_normal);

						oldPosition = position;
						currentItem = position;
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {

					}
				});

	}

	/* *
	 * 重新设定line的宽
	 */
	private void initLineImage() {
		// TODO Auto-generated method stub
		/**
		 * * 获取屏幕的宽
		 */
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		/**
		 * 重新设置下划线的宽度
		 */
		LayoutParams lp = line_tab.getLayoutParams();
		lp.width = screenW / 4;
		line_tab.setLayoutParams(lp);

		moveOne = lp.width; // 滑动页面的距
	}

	private void initView() {
		// TODO Auto-generated method stub
		myViewPager = (ViewPager) findViewById(R.id.myViewPager);

		// ViewPager中包含的页面为Fragment
		MyFragment1 myFragment1 = new MyFragment1();
		MyFragment2 myFragment2 = new MyFragment2();
		MyFragment3 myFragment3 = new MyFragment3();
		MyFragment4 myFragment4 = new MyFragment4();

		List<Fragment> fragmentList = new ArrayList<Fragment>();

		fragmentList.add(myFragment1);
		fragmentList.add(myFragment2);
		fragmentList.add(myFragment3);
		fragmentList.add(myFragment4);

		MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(
				getSupportFragmentManager(), fragmentList);
		myViewPager.setAdapter(myFragmentAdapter);

		tv_tab0 = (TextView) findViewById(R.id.tv_tab0);
		tv_tab1 = (TextView) findViewById(R.id.tv_tab1);
		tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
		tv_tab3 = (TextView) findViewById(R.id.tv_tab3);

		myViewPager.setCurrentItem(0);
		tv_tab0.setTextColor(Color.GRAY);
		tv_tab1.setTextColor(Color.BLACK);
		tv_tab2.setTextColor(Color.BLACK);
		tv_tab3.setTextColor(Color.BLACK);

		tv_tab0.setOnClickListener(this);
		tv_tab1.setOnClickListener(this);
		tv_tab2.setOnClickListener(this);
		tv_tab3.setOnClickListener(this);

		myViewPager.setOnPageChangeListener(this);

		line_tab = (ImageView) findViewById(R.id.line_tab);
		
		
	}

	// 页面在滑动过程中不停触发该方法，时间�?
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub

		currentTime = System.currentTimeMillis();
		if (isScrolling && (currentTime - startTime > 200)) {
			movePositionX(position, moveOne * positionOffset);
			startTime = currentTime;
		}

		if (isBackScrolling) {
			movePositionX(position);
		}
	}

	// 当页面的滑动状态改变时该方法会被触发，页面的滑动状�?�?个：
	// �?”表示什么都不做，�?1”表示开始滑动，�?”表示结束滑动�?
	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO 自动生成的方法存�?
		switch (state) {
		case 1:
			isScrolling = true;
			isBackScrolling = false;
			break;
		case 2:
			isScrolling = false;
			isBackScrolling = true;
			break;
		default:
			isScrolling = false;
			isBackScrolling = false;
			break;
		}
	}

	// ViewPager跳转到新页面时触发该方法，position表示新页面的位置
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			tv_tab0.setTextColor(Color.GRAY);
			tv_tab1.setTextColor(Color.BLACK);
			tv_tab2.setTextColor(Color.BLACK);
			tv_tab3.setTextColor(Color.BLACK);
			movePositionX(0);
			break;
		case 1:
			tv_tab0.setTextColor(Color.BLACK);
			tv_tab1.setTextColor(Color.GRAY);
			tv_tab2.setTextColor(Color.BLACK);
			tv_tab3.setTextColor(Color.BLACK);
			movePositionX(1);
			break;
		case 2:
			tv_tab0.setTextColor(Color.BLACK);
			tv_tab1.setTextColor(Color.BLACK);
			tv_tab2.setTextColor(Color.GRAY);
			tv_tab3.setTextColor(Color.BLACK);
			movePositionX(2);
			break;
		case 3:
			tv_tab0.setTextColor(Color.BLACK);
			tv_tab1.setTextColor(Color.BLACK);
			tv_tab2.setTextColor(Color.BLACK);
			tv_tab3.setTextColor(Color.GRAY);
			movePositionX(3);
			break;
		default:
			break;
		}
	}

	// 下滑縣隨手指的滑动而移动
	@SuppressLint("NewApi")
	private void movePositionX(int toPosition, float positionOffsetPixels) {
		// TODO Auto-generated method stub
		float curTranslationX = line_tab.getTranslationX();
		float toPositionX = moveOne * toPosition + positionOffsetPixels;
		ObjectAnimator animator = ObjectAnimator.ofFloat(line_tab,
				"translationX", curTranslationX, toPositionX);
		animator.setDuration(500);
		animator.start();
	}

	// 下划线移动到新的选卡
	private void movePositionX(int toPosition) {
		// TODO Auto-generated method stub
		movePositionX(toPosition, 0);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		//FragmentManager fg = getSupportFragmentManager();
		//FragmentTransaction ft = fg.beginTransaction();

		switch (v.getId()) {
		case R.id.tv_tab0:
			myViewPager.setCurrentItem(0);
			break;
		case R.id.tv_tab1:
			myViewPager.setCurrentItem(1);
			break;
		case R.id.tv_tab2:
			myViewPager.setCurrentItem(2);
			break;
		case R.id.tv_tab3:
			myViewPager.setCurrentItem(3);
			break;
		case R.id.id_indicator_one:
			resetOtherTabs();
			mTabIndicator.get(0).setIconAlpha(1.0f);

			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			finish();
			// overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			break;
		case R.id.id_indicator_two:
			resetOtherTabs();
			
			  Intent intent2 = new Intent();
			  intent2.setClass(MainActivity.this, MainActivity1.class);
			  startActivity(intent2);
			  overridePendingTransition(android.R.anim.fade_in,
			  android.R.anim.fade_out);
			  finish();
			  
			  //mTabIndicator.get(1).setIconAlpha(1.0f);
				//ft.replace(R.id.main_frame,
					//	new StuFragment(getApplicationContext()));
				
			// overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			break;
		case R.id.id_indicator_three:
			resetOtherTabs();
			mTabIndicator.get(2).setIconAlpha(1.0f);
			 Intent intent3 = new Intent();
			  intent3.setClass(MainActivity.this, MainActivity2.class);
			  startActivity(intent3);
			  overridePendingTransition(android.R.anim.fade_in,
			  android.R.anim.fade_out);
			
			//ft.replace(R.id.main_frame, new TeachFragment());
			break;
		case R.id.id_indicator_four:
			resetOtherTabs();
			mTabIndicator.get(3).setIconAlpha(1.0f);
			Intent intent4 = new Intent();
			  intent4.setClass(MainActivity.this, MainActivity3.class);
			  startActivity(intent4);
			  overridePendingTransition(android.R.anim.fade_in,
			  android.R.anim.fade_out);
			  finish();
			
			//ft.replace(R.id.main_frame, new OtherFragment());
			break;
		default:
			break;
		}
		
	}

	/**
	 * 自定义Adapter
	 * 
	 */
	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			// TODO Auto-generated method stub
			// super.destroyItem(container, position, object);
			// view.removeView(view.getChildAt(position));
			// view.removeViewAt(position);
			view.removeView(images.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			// TODO Auto-generated method stub
			view.addView(images.get(position));
			return images.get(position);
		}

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 利用线程池定时执行动画轮播
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 10,
				10, TimeUnit.SECONDS);
	}

	private class ViewPageTask implements Runnable {

		@Override
		public void run() {
			currentItem = (currentItem + 1) % imageIds.length;
			mHandler.sendEmptyMessage(0);
		}
	}

	/**
	 * 接收子线程传递过来的数据
	 */

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			mViewPaper.setCurrentItem(currentItem);
		};
	};

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
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

		one.setIconAlpha(1.0f);

		
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
